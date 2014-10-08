package com.yangc.calendar.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yangc.calendar.R;
import com.yangc.calendar.dialog.AboutDialog;
import com.yangc.calendar.fragment.DayFragment;
import com.yangc.calendar.fragment.MonthFragment;
import com.yangc.calendar.utils.Constants;

public class MainActivity extends FragmentActivity {

	private AboutDialog aboutDialog;

	private int currentTabId;
	private SparseArrayCompat<Fragment> fragments;
	private SparseArrayCompat<RadioButton> radioButtons;

	private TextView tvTitleBarContent;

	private int colorTabNormal;
	private int colorTabSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		this.aboutDialog = new AboutDialog(this, R.style.prompt_dialog);

		this.fragments = new SparseArrayCompat<Fragment>(2);
		this.fragments.put(R.id.rb_tabDay, new DayFragment(this));
		this.fragments.put(R.id.rb_tabMonth, new MonthFragment(this));

		LinearLayout llTitleBar = (LinearLayout) this.findViewById(R.id.ll_titleBar);
		llTitleBar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = fragments.get(currentTabId);
				if (fragment instanceof DayFragment) {
					((DayFragment) fragment).showYMDDialog();
				} else if (fragment instanceof MonthFragment) {
					((MonthFragment) fragment).showYMDialog();
				}
			}
		});
		this.tvTitleBarContent = (TextView) this.findViewById(R.id.tv_titleBarContent);

		this.colorTabNormal = this.getResources().getColor(R.color.tab_normal);
		this.colorTabSelect = this.getResources().getColor(R.color.tab_select);

		RadioGroup rgTabs = (RadioGroup) this.findViewById(R.id.rg_tabs);
		rgTabs.setOnCheckedChangeListener(new CheckedChangeListener());

		this.radioButtons = new SparseArrayCompat<RadioButton>(2);
		this.radioButtons.put(R.id.rb_tabDay, (RadioButton) this.findViewById(R.id.rb_tabDay));
		this.radioButtons.put(R.id.rb_tabMonth, (RadioButton) this.findViewById(R.id.rb_tabMonth));

		this.radioButtons.get(R.id.rb_tabDay).setChecked(true);
	}

	public void setTitleBarText(String text) {
		this.tvTitleBarContent.setText(text);
	}

	private class CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			currentTabId = checkedId;

			for (int i = 0; i < radioButtons.size(); i++) {
				radioButtons.valueAt(i).setTextColor(radioButtons.keyAt(i) == checkedId ? colorTabSelect : colorTabNormal);
			}

			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.rl_main, fragments.get(checkedId));
			fragmentTransaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_about) {
			this.aboutDialog.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (!Constants.isExit) {
				Constants.isExit = true;
				Toast.makeText(this, R.string.text_exit, Toast.LENGTH_SHORT).show();
				// 如果3秒内不按第二次的话, 退出状态恢复
				exitHandler.sendEmptyMessageDelayed(0, 3000);
			} else {
				this.finish();
				System.exit(0);
			}
			return true;
		}
		return false;
	}

	private static Handler exitHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Constants.isExit = false;
		}
	};

}
