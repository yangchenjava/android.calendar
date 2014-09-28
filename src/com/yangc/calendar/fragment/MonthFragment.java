package com.yangc.calendar.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yangc.calendar.R;

public class MonthFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_month, container, false);
		TextView textView = (TextView) view.findViewById(R.id.tv_fragmentMonth);
		textView.setText("MonthFragment");
		return view;
	}

}
