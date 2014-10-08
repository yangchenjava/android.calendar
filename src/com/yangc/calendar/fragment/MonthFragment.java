package com.yangc.calendar.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.yangc.calendar.R;
import com.yangc.calendar.activity.MainActivity;
import com.yangc.calendar.bean.CalendarBean;
import com.yangc.calendar.dialog.YMDialog;
import com.yangc.calendar.fragment.adapter.MonthFragmentAdapter;
import com.yangc.calendar.utils.ChineseCalendar;
import com.yangc.calendar.utils.Constants;
import com.yangc.calendar.utils.DateUtils;

public class MonthFragment extends Fragment {

	// 最多可以显示12000个月
	private static final int ITEM_COUNT = 12000;

	private MainActivity mainActivity;
	private ViewPager vpFragmentMonth;
	private YMDialog ymDialog;

	private String dateSelected;

	public MonthFragment(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_month, container, false);
		this.vpFragmentMonth = (ViewPager) view.findViewById(R.id.vp_fragmentMonth);
		this.vpFragmentMonth.setOffscreenPageLimit(2);
		this.vpFragmentMonth.setAdapter(new MonthFragmentPagerAdapter());
		this.vpFragmentMonth.setOnPageChangeListener(new PageChangeListener());
		this.ymDialog = new YMDialog(this.mainActivity, R.style.prompt_dialog, new YMDialog.OnDateSetListener() {
			@Override
			public void onDateSet(int year, int monthOfYear) {
				setViewPagerCurrentItem(year, monthOfYear, 1);
			}
		}, new YMDialog.OnClickListener() {
			@Override
			public void onClick() {
				setToday();
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		this.setToday();
	}

	private void setToday() {
		this.mainActivity.setTitleBarText(DateFormat.format("yyyy-MM", System.currentTimeMillis()).toString());
		this.vpFragmentMonth.setCurrentItem(ITEM_COUNT / 2);
	}

	private void setViewPagerCurrentItem(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		this.vpFragmentMonth.setCurrentItem(ITEM_COUNT / 2 + DateUtils.getOffsetMonths(Calendar.getInstance(), calendar));
	}

	private class MonthFragmentPagerAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			return ITEM_COUNT;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, position - ITEM_COUNT / 2);

			View view = mainActivity.getLayoutInflater().inflate(R.layout.fragment_month_item, container, false);
			GridView gvFragmentMonthGrid = (GridView) view.findViewById(R.id.gv_fragmentMonth_grid);
			gvFragmentMonthGrid.setAdapter(new MonthFragmentAdapter(mainActivity, getCalendarBeanList(calendar)));

			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	private class PageChangeListener implements ViewPager.OnPageChangeListener {
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, position - ITEM_COUNT / 2);
			dateSelected = DateFormat.format("yyyy-MM", calendar).toString();
			if (dateSelected.equals(Constants.BEGIN_MONTH)) {
				calendar.add(Calendar.MONTH, 1);
				setViewPagerCurrentItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
				Toast.makeText(mainActivity, R.string.text_prompt, Toast.LENGTH_SHORT).show();
			} else if (dateSelected.equals(Constants.END_MONTH)) {
				calendar.add(Calendar.MONTH, -1);
				setViewPagerCurrentItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
				Toast.makeText(mainActivity, R.string.text_prompt, Toast.LENGTH_SHORT).show();
			} else {
				mainActivity.setTitleBarText(dateSelected);
			}
		}

		// 状态有三个: 0空闲, 1正在滑行中, 2目标加载完毕
		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	private List<CalendarBean> getCalendarBeanList(Calendar calendar) {
		List<CalendarBean> list = new ArrayList<CalendarBean>();

		int currentMonth = calendar.get(Calendar.MONTH);

		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - calendar.get(Calendar.DAY_OF_WEEK));

		ChineseCalendar chineseCalendar = new ChineseCalendar(calendar);
		ChineseCalendar cc = new ChineseCalendar(true, Integer.parseInt(chineseCalendar.getSimpleChineseDateString().substring(0, 4)), 12, 30);
		// 先调一下这个, 后面的getTime()才能生效
		cc.getSimpleGregorianDateString();
		// 除夕的公历日期
		CharSequence newYearEve = DateFormat.format("yyyyMMdd", cc.getTime());

		for (int i = 0; i < 42; i++) {
			CalendarBean bean = new CalendarBean();
			bean.setDay("" + calendar.get(Calendar.DAY_OF_MONTH));
			chineseCalendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

			CharSequence ymdStr = DateFormat.format("yyyyMMdd", calendar.getTime());
			CharSequence mdStr = ymdStr.subSequence(4, ymdStr.length());
			if (ymdStr.equals(newYearEve)) {
				bean.setChineseDay("除夕");
				bean.setFestival(true);
			} else if (Constants.FESTIVAL.containsKey(mdStr)) {
				bean.setChineseDay(Constants.FESTIVAL.get(mdStr));
				bean.setFestival(true);
			} else {
				String key = chineseCalendar.getChinese(ChineseCalendar.CHINESE_MONTH) + chineseCalendar.getChinese(ChineseCalendar.CHINESE_DATE);
				if (Constants.FESTIVAL.containsKey(key)) {
					bean.setChineseDay(Constants.FESTIVAL.get(key));
					bean.setFestival(true);
				}
			}
			if (bean.getChineseDay() == null) {
				String chineseDateName = chineseCalendar.getChinese(ChineseCalendar.CHINESE_TERM_OR_DATE);
				bean.setChineseDay(chineseDateName);
				bean.setFestival(ChineseCalendar.isTerm(chineseDateName));
			}

			// 判断是否为周末
			bean.setWeekend(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);

			// 判断日期状态
			if (DateUtils.isToday(calendar.getTimeInMillis())) {
				bean.setDateState(1);
			} else if (currentMonth != calendar.get(Calendar.MONTH)) {
				bean.setDateState(0);
			} else {
				bean.setDateState(2);
			}

			list.add(bean);
			calendar.add(Calendar.DATE, 1);
		}
		return list;
	}

	public void showYMDialog() {
		String[] ym = this.dateSelected.split("-");
		this.ymDialog.showDialog(Integer.parseInt(ym[0]), Integer.parseInt(ym[1]) - 1);
	}

}
