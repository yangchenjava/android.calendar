package com.yangc.calendar.dialog;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yangc.calendar.R;
import com.yangc.calendar.utils.Constants;

public class YMDialog extends Dialog {

	private Context context;
	private OnDateSetListener dateSetListener;
	private OnClickListener clickListener;

	private TextView title;

	private WheelView yearWheel;
	private WheelView monthWheel;

	public YMDialog(Context context) {
		super(context);
		this.context = context;
	}

	public YMDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public YMDialog(Context context, int theme, OnDateSetListener dateSetListener, OnClickListener clickListener) {
		super(context, theme);
		this.context = context;
		this.dateSetListener = dateSetListener;
		this.clickListener = clickListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_ym);

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.width = 450;
		params.height = 420;
		this.getWindow().setAttributes(params);

		title = (TextView) this.findViewById(R.id.tv_dialogYm_title);

		yearWheel = (WheelView) this.findViewById(R.id.wv_dialogYm_year);
		yearWheel.setViewAdapter(new NumericWheelAdapter(this.context, Constants.MIN_YEAR, Constants.MAX_YEAR));
		yearWheel.setCyclic(true);
		yearWheel.addChangingListener(new WheelChangedListener());

		monthWheel = (WheelView) this.findViewById(R.id.wv_dialogYm_month);
		monthWheel.setViewAdapter(new NumericWheelAdapter(this.context, 1, 12, "%02d"));
		monthWheel.setCyclic(true);
		monthWheel.addChangingListener(new WheelChangedListener());

		TextView setBtn = (TextView) this.findViewById(R.id.tv_dialogYm_set);
		setBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dateSetListener.onDateSet(yearWheel.getCurrentItem() + Constants.MIN_YEAR, monthWheel.getCurrentItem());
				dismiss();
			}
		});

		TextView todayBtn = (TextView) this.findViewById(R.id.tv_dialogYm_today);
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
			title.setText((yearWheel.getCurrentItem() + Constants.MIN_YEAR) + "年" + String.format("%02d", monthWheel.getCurrentItem() + 1) + "月");
		}
	}

	public void showDialog(int year, int monthOfYear) {
		this.show();
		title.setText(year + "年" + String.format("%02d", monthOfYear + 1) + "月");
		yearWheel.setCurrentItem(year - Constants.MIN_YEAR);
		monthWheel.setCurrentItem(monthOfYear);
	}

	public interface OnDateSetListener {
		void onDateSet(int year, int monthOfYear);
	}

	public interface OnClickListener {
		void onClick();
	}

}
