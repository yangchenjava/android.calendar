package com.yangc.calendar.fragment;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

import android.os.Bundle;
import android.os.Handler;
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
import com.yangc.calendar.dialog.YMDialog;
import com.yangc.calendar.fragment.asynctask.MonthAsyncTask;
import com.yangc.calendar.utils.Constants;
import com.yangc.calendar.utils.DateUtils;

public class MonthFragment extends Fragment {

	// 最多可以显示12000个月
	public static final int ITEM_COUNT = 12000;
	public static AtomicBoolean ONCE = new AtomicBoolean();

	private MainActivity mainActivity;
	private ViewPager vpFragmentMonth;
	private YMDialog ymDialog;

	private String dateSelected;

	public MonthFragment(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		MonthFragment.ONCE.set(true);
		View view = inflater.inflate(R.layout.fragment_month, container, false);
		this.vpFragmentMonth = (ViewPager) view.findViewById(R.id.vp_fragmentMonth);
		this.vpFragmentMonth.setOffscreenPageLimit(2);
		this.vpFragmentMonth.setAdapter(new MonthFragmentPagerAdapter());
		this.vpFragmentMonth.setOnPageChangeListener(new PageChangeListener());
		this.ymDialog = new YMDialog(this.mainActivity, R.style.prompt_dialog, new YMDialog.OnDateSetListener() {
			@Override
			public void onDateSet(int year, int monthOfYear) {
				MonthFragment.ONCE.set(true);
				setViewPagerCurrentItem(year, monthOfYear, 1);
			}
		}, new YMDialog.OnClickListener() {
			@Override
			public void onClick() {
				MonthFragment.ONCE.set(true);
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
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = mainActivity.getLayoutInflater().inflate(R.layout.fragment_month_item, container, false);
			new MonthAsyncTask(mainActivity, (GridView) view.findViewById(R.id.gv_fragmentMonth_grid)).execute(position);
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
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						mainActivity.setTitleBarText(dateSelected);
					}
				});
			}
		}

		// 状态有三个: 0空闲, 1正在滑行中, 2目标加载完毕
		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

	public void showYMDialog() {
		String[] ym = this.dateSelected.split("-");
		this.ymDialog.showDialog(Integer.parseInt(ym[0]), Integer.parseInt(ym[1]) - 1);
	}

}
