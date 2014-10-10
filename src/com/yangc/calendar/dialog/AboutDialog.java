package com.yangc.calendar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.yangc.calendar.R;
import com.yangc.calendar.utils.Constants;

public class AboutDialog extends Dialog {

	public AboutDialog(Context context) {
		super(context);
	}

	public AboutDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_about);

		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.width = (int) (Constants.SCREEN_WIDTH * 0.8);
		params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		this.getWindow().setAttributes(params);

		((RelativeLayout) this.findViewById(R.id.rl_dialogAbout_cancel)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

}
