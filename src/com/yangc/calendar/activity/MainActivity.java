package com.yangc.calendar.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yangc.calendar.R;
import com.yangc.calendar.fragment.DayFragment;
import com.yangc.calendar.fragment.MonthFragment;

public class MainActivity extends FragmentActivity {

	private SparseArrayCompat<Fragment> fragments;

	private SparseArrayCompat<RadioButton> radioButtons;

	private int colorTabNormal;
	private int colorTabSelect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		this.colorTabNormal = this.getResources().getColor(R.color.tab_normal);
		this.colorTabSelect = this.getResources().getColor(R.color.tab_select);

		this.fragments = new SparseArrayCompat<Fragment>(2);
		this.fragments.put(R.id.button_rb_tabDay, new DayFragment());
		this.fragments.put(R.id.button_rb_tabMonth, new MonthFragment());

		RadioGroup groupRgTabs = (RadioGroup) this.findViewById(R.id.group_rg_tabs);
		groupRgTabs.setOnCheckedChangeListener(new CheckedChangeListener());

		this.radioButtons = new SparseArrayCompat<RadioButton>(2);
		this.radioButtons.put(R.id.button_rb_tabDay, (RadioButton) this.findViewById(R.id.button_rb_tabDay));
		this.radioButtons.put(R.id.button_rb_tabMonth, (RadioButton) this.findViewById(R.id.button_rb_tabMonth));

		this.radioButtons.get(R.id.button_rb_tabDay).setChecked(true);
	}

	private class CheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			for (int i = 0; i < radioButtons.size(); i++) {
				radioButtons.valueAt(i).setTextColor(radioButtons.keyAt(i) == checkedId ? colorTabSelect : colorTabNormal);
			}

			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.layout_rl_main, fragments.get(checkedId));
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
		int itemId = item.getItemId();
		if (itemId == R.id.menu_about) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
