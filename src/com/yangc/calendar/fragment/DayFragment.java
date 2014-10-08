package com.yangc.calendar.fragment;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yangc.calendar.R;
import com.yangc.calendar.activity.MainActivity;
import com.yangc.calendar.dialog.YMDDialog;
import com.yangc.calendar.utils.ChineseCalendar;
import com.yangc.calendar.utils.Constants;
import com.yangc.calendar.utils.DateUtils;
import com.yangc.calendar.utils.DivineUtils;

public class DayFragment extends Fragment {

	private static final int ITEM_COUNT = 4380000;

	private MainActivity mainActivity;
	private ViewPager vpFragmentDay;
	private YMDDialog ymdDialog;

	private String dateSelected;

	public DayFragment(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_day, container, false);
		this.vpFragmentDay = (ViewPager) view.findViewById(R.id.vp_fragmentDay);
		this.vpFragmentDay.setOffscreenPageLimit(2);
		this.vpFragmentDay.setAdapter(new DayFragmentPagerAdapter());
		this.vpFragmentDay.setOnPageChangeListener(new PageChangeListener());
		this.ymdDialog = new YMDDialog(this.mainActivity, R.style.prompt_dialog, new YMDDialog.OnDateSetListener() {
			@Override
			public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
				setViewPagerCurrentItem(year, monthOfYear, dayOfMonth);
			}
		}, new YMDDialog.OnClickListener() {
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
		this.vpFragmentDay.setCurrentItem(ITEM_COUNT / 2);
	}

	private void setViewPagerCurrentItem(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		this.vpFragmentDay.setCurrentItem(ITEM_COUNT / 2 + DateUtils.getOffsetDays(Calendar.getInstance(), calendar));
	}

	private class DayFragmentPagerAdapter extends PagerAdapter {
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
			calendar.add(Calendar.DATE, position - ITEM_COUNT / 2);
			ChineseCalendar chineseCalendar = new ChineseCalendar(calendar);

			int day = calendar.get(Calendar.DATE);

			View view = mainActivity.getLayoutInflater().inflate(R.layout.fragment_day_item, container, false);
			((TextView) view.findViewById(R.id.tv_fragmentDay_day)).setText("" + day);
			((TextView) view.findViewById(R.id.tv_fragmentDay_ch_year)).setText(chineseCalendar.getChinese(ChineseCalendar.CHINESE_YEAR) + " ["
					+ chineseCalendar.getChinese(ChineseCalendar.CHINESE_ZODIAC) + "年]");
			((TextView) view.findViewById(R.id.tv_fragmentDay_ch_month)).setText("农历" + chineseCalendar.getChinese(ChineseCalendar.CHINESE_MONTH)
					+ chineseCalendar.getChinese(ChineseCalendar.CHINESE_DATE));
			((TextView) view.findViewById(R.id.tv_fragmentDay_ch_day)).setText(chineseCalendar.getChinese(ChineseCalendar.DAY_OF_WEEK));

			DivineUtils.setGoodBad((TextView) view.findViewById(R.id.tv_fragmentDay_good_1), (TextView) view.findViewById(R.id.tv_fragmentDay_goodContent_1),
					(TextView) view.findViewById(R.id.tv_fragmentDay_good_2), (TextView) view.findViewById(R.id.tv_fragmentDay_goodContent_2), (TextView) view.findViewById(R.id.tv_fragmentDay_bad_1),
					(TextView) view.findViewById(R.id.tv_fragmentDay_badContent_1), (TextView) view.findViewById(R.id.tv_fragmentDay_bad_2),
					(TextView) view.findViewById(R.id.tv_fragmentDay_badContent_2), day);
			DivineUtils.setFace(mainActivity, (TextView) view.findViewById(R.id.tv_fragmentDay_face), day);
			DivineUtils.setDrink(mainActivity, (TextView) view.findViewById(R.id.tv_fragmentDay_drink), day);
			DivineUtils.setGirl((TextView) view.findViewById(R.id.tv_fragmentDay_girl), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, day);

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
			calendar.add(Calendar.DATE, position - ITEM_COUNT / 2);
			dateSelected = DateFormat.format("yyyy-MM-dd", calendar).toString();
			if (dateSelected.equals(Constants.BEGIN_DATE)) {
				calendar.add(Calendar.DATE, 1);
				setViewPagerCurrentItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
				Toast.makeText(mainActivity, R.string.text_prompt, Toast.LENGTH_SHORT).show();
			} else if (dateSelected.equals(Constants.END_MONTH)) {
				calendar.add(Calendar.DATE, -1);
				setViewPagerCurrentItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
				Toast.makeText(mainActivity, R.string.text_prompt, Toast.LENGTH_SHORT).show();
			} else {
				mainActivity.setTitleBarText(dateSelected.substring(0, 7));
			}
		}

		// 状态有三个: 0空闲, 1正在滑行中, 2目标加载完毕
		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	public void showYMDDialog() {
		String[] ymd = this.dateSelected.split("-");
		this.ymdDialog.showDialog(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]) - 1, Integer.parseInt(ymd[2]));
	}

}
