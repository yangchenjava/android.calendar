package com.yangc.calendar.dialog;

import java.util.Calendar;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.yangc.calendar.R;
import com.yangc.calendar.utils.Constants;

public class YMDDialog extends Dialog {

	private Context context;
	private OnDateSetListener dateSetListener;
	private OnClickListener clickListener;

	private TextView title;

	private WheelView yearWheel;
	private WheelView monthWheel;
	private WheelView dayWheel;

	public YMDDialog(Context context) {
		super(context);
		this.context = context;
	}

	public YMDDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public YMDDialog(Context context, int theme, OnDateSetListener dateSetListener, OnClickListener clickListener) {
		super(context, theme);
		this.context = context;
		this.dateSetListener = dateSetListener;
		this.clickListener = clickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_ymd);

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.width = (int) (Constants.SCREEN_WIDTH * 0.9);
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		this.getWindow().setAttributes(params);

		title = (TextView) this.findViewById(R.id.tv_dialogYmd_title);

		yearWheel = (WheelView) this.findViewById(R.id.wv_dialogYmd_year);
		yearWheel.setViewAdapter(new NumericWheelAdapter(this.context, Constants.MIN_YEAR, Constants.MAX_YEAR));
		yearWheel.setCyclic(true);
		yearWheel.addChangingListener(new WheelChangedListener());

		monthWheel = (WheelView) this.findViewById(R.id.wv_dialogYmd_month);
		monthWheel.setViewAdapter(new NumericWheelAdapter(this.context, 1, 12, "%02d"));
		monthWheel.setCyclic(true);
		monthWheel.addChangingListener(new WheelChangedListener());

		dayWheel = (WheelView) this.findViewById(R.id.wv_dialogYmd_day);
		dayWheel.setCyclic(true);
		dayWheel.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				title.setText((yearWheel.getCurrentItem() + Constants.MIN_YEAR) + "年" + String.format("%02d", monthWheel.getCurrentItem() + 1) + "月"
						+ String.format("%02d", dayWheel.getCurrentItem() + 1) + "日");
			}
		});

		TextView setBtn = (TextView) this.findViewById(R.id.tv_dialogYmd_set);
		setBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dateSetListener.onDateSet(yearWheel.getCurrentItem() + Constants.MIN_YEAR, monthWheel.getCurrentItem(), dayWheel.getCurrentItem() + 1);
				dismiss();
			}
		});

		TextView todayBtn = (TextView) this.findViewById(R.id.tv_dialogYmd_today);
		todayBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clickListener.onClick();
				dismiss();
			}
		});
	}

	private class WheelChangedListener implements OnWheelChangedListener {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(yearWheel.getCurrentItem() + Constants.MIN_YEAR, monthWheel.getCurrentItem(), 1);
			int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			dayWheel.setViewAdapter(new NumericWheelAdapter(context, 1, maxDay, "%02d"));
			if (dayWheel.getCurrentItem() + 1 > maxDay) {
				dayWheel.setCurrentItem(maxDay - 1);
			}
			title.setText((yearWheel.getCurrentItem() + Constants.MIN_YEAR) + "年" + String.format("%02d", monthWheel.getCurrentItem() + 1) + "月" + String.format("%02d", dayWheel.getCurrentItem() + 1)
					+ "日");
		}
	}

	public void showDialog(int year, int monthOfYear, int dayOfMonth) {
		this.show();
		title.setText(year + "年" + String.format("%02d", monthOfYear + 1) + "月" + String.format("%02d", dayOfMonth) + "日");
		yearWheel.setCurrentItem(year - Constants.MIN_YEAR);
		monthWheel.setCurrentItem(monthOfYear);
		dayWheel.setCurrentItem(dayOfMonth - 1);
	}

	public interface OnDateSetListener {
		void onDateSet(int year, int monthOfYear, int dayOfMonth);
	}

	public interface OnClickListener {
		void onClick();
	}

}
