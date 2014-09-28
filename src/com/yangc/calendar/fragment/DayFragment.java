package com.yangc.calendar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yangc.calendar.R;

public class DayFragment extends Fragment {

	private static final int ITEM_COUNT = 4380000;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_day, container, false);
		ViewPager viewVpFragmentDay = (ViewPager) view.findViewById(R.id.vp_fragmentDay);
		viewVpFragmentDay.setOffscreenPageLimit(2);
		viewVpFragmentDay.setAdapter(new DayFragmentPagerAdapter());
		viewVpFragmentDay.setOnPageChangeListener(new PageChangeListener());
		return view;
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

			return super.instantiateItem(container, position);
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

		}

		// 状态有三个: 0空闲, 1正在滑行中, 2目标加载完毕
		@Override
		public void onPageScrollStateChanged(int state) {
		}
	}

}
