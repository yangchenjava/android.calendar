package com.yangc.calendar.fragment.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yangc.calendar.R;
import com.yangc.calendar.bean.CalendarBean;

public class MonthFragmentAdapter extends BaseAdapter {

	private Context context;
	private List<CalendarBean> list;

	public MonthFragmentAdapter(Context context, List<CalendarBean> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = View.inflate(context, R.layout.fragment_month_item_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvFragmentMonthItemDay = (TextView) view.findViewById(R.id.tv_fragmentMonthItem_day);
			viewHolder.tvFragmentMonthItemChineseDay = (TextView) view.findViewById(R.id.tv_fragmentMonthItem_chineseDay);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		CalendarBean bean = list.get(position);
		viewHolder.tvFragmentMonthItemDay.setText(bean.getDay());
		viewHolder.tvFragmentMonthItemChineseDay.setText(bean.getChineseDay());
		if (bean.getDateState() == 0) {
			viewHolder.tvFragmentMonthItemDay.setTextColor(context.getResources().getColor(R.color.date_state_0));
			viewHolder.tvFragmentMonthItemChineseDay.setTextColor(context.getResources().getColor(R.color.date_state_0));
			viewHolder.tvFragmentMonthItemDay.setBackgroundResource(R.color.white);
			viewHolder.tvFragmentMonthItemChineseDay.setBackgroundResource(R.color.white);
		} else if (bean.getDateState() == 1) {
			viewHolder.tvFragmentMonthItemDay.setTextColor(context.getResources().getColor(R.color.date_state_1));
			viewHolder.tvFragmentMonthItemChineseDay.setTextColor(context.getResources().getColor(R.color.date_state_1));
			viewHolder.tvFragmentMonthItemDay.setBackgroundResource(R.color.grid_item_bg);
			viewHolder.tvFragmentMonthItemChineseDay.setBackgroundResource(R.color.grid_item_bg);
		} else {
			if (bean.isWeekend()) {
				viewHolder.tvFragmentMonthItemDay.setTextColor(context.getResources().getColor(R.color.date_state_weekend));
			} else {
				viewHolder.tvFragmentMonthItemDay.setTextColor(context.getResources().getColor(R.color.date_state_2));
			}
			if (bean.isFestival()) {
				viewHolder.tvFragmentMonthItemChineseDay.setTextColor(context.getResources().getColor(R.color.date_state_festival));
			} else {
				viewHolder.tvFragmentMonthItemChineseDay.setTextColor(context.getResources().getColor(R.color.date_state_2));
			}
			viewHolder.tvFragmentMonthItemDay.setBackgroundResource(R.color.white);
			viewHolder.tvFragmentMonthItemChineseDay.setBackgroundResource(R.color.white);
		}
		return view;
	}

	private class ViewHolder {
		TextView tvFragmentMonthItemDay;
		TextView tvFragmentMonthItemChineseDay;
	}

}