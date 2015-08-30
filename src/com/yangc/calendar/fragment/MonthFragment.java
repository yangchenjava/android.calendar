package com.yangc.calendar.fragment;

import java.util.concurrent.atomic.AtomicBoolean;

import org.joda.time.LocalDate;
import org.joda.time.Months;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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

public class MonthFragment extends Fragment {

	// 最多可以显示12000个月
	public static final int ITEM_COUNT = 12000;
	public static AtomicBoolean ONCE = new AtomicBoolean();

	private ViewPager vpFragmentMonth;
	private YMDialog ymDialog;

	private String dateSelected;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		MonthFragment.ONCE.set(true);
		View view = inflater.inflate(R.layout.fragment_month, container, false);
		this.vpFragmentMonth = (ViewPager) view.findViewById(R.id.vp_fragmentMonth);
		this.vpFragmentMonth.setOffscreenPageLimit(2);
		this.vpFragmentMonth.setAdapter(new MonthFragmentPagerAdapter());
		this.vpFragmentMonth.setOnPageChangeListener(new PageChangeListener());
		this.ymDialog = new YMDialog(this.getActivity(), R.style.prompt_dialog, new YMDialog.OnDateSetListener() {
			@Override
			public void onDateSet(int year, int monthOfYear) {
				MonthFragment.ONCE.set(true);
				setViewPagerCurrentItem(year, monthOfYear + 1);
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
		((MainActivity) this.getActivity()).setTitleBarText(LocalDate.now().toString("yyyy-MM"));
		this.vpFragmentMonth.setCurrentItem(ITEM_COUNT / 2);
	}

	private void setViewPagerCurrentItem(int year, int month) {
		this.vpFragmentMonth.setCurrentItem(ITEM_COUNT / 2 + Months.monthsBetween(LocalDate.now().withDayOfMonth(1), new LocalDate(year, month, 1)).getMonths());
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
			View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_month_item, container, false);
			new MonthAsyncTask(getActivity(), (GridView) view.findViewById(R.id.gv_fragmentMonth_grid)).execute(position - ITEM_COUNT / 2);
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
			LocalDate ld = LocalDate.now().plusMonths(position - ITEM_COUNT / 2);
			dateSelected = ld.toString("yyyy-MM");
			if (dateSelected.equals(Constants.BEGIN_MONTH)) {
				ld = ld.plusMonths(1);
				setViewPagerCurrentItem(ld.getYear(), ld.getMonthOfYear());
				Toast.makeText(getActivity(), R.string.text_prompt, Toast.LENGTH_SHORT).show();
			} else if (dateSelected.equals(Constants.END_MONTH)) {
				ld = ld.plusMonths(-1);
				setViewPagerCurrentItem(ld.getYear(), ld.getMonthOfYear());
				Toast.makeText(getActivity(), R.string.text_prompt, Toast.LENGTH_SHORT).show();
			} else {
				new Handler().post(new Runnable() {
					@Override
					public void run() {
						((MainActivity) getActivity()).setTitleBarText(dateSelected);
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
