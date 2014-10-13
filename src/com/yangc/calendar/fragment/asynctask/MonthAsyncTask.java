package com.yangc.calendar.fragment.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.yangc.calendar.R;
import com.yangc.calendar.bean.CalendarBean;
import com.yangc.calendar.fragment.MonthFragment;
import com.yangc.calendar.fragment.adapter.MonthFragmentAdapter;
import com.yangc.calendar.utils.ChineseCalendar;
import com.yangc.calendar.utils.Constants;

public class MonthAsyncTask extends AsyncTask<Integer, Integer, BaseAdapter> {

	private Context context;
	private GridView mGridView;
	private ProgressDialog progressDialog;

	public MonthAsyncTask(Context context, GridView mGridView) {
		this.context = context;
		this.mGridView = mGridView;
	}

	@Override
	protected void onPreExecute() {
		if (MonthFragment.ONCE.compareAndSet(true, false)) {
			this.progressDialog = ProgressDialog.show(this.context, "", this.context.getResources().getString(R.string.text_load), true);
		}
	}

	@Override
	protected BaseAdapter doInBackground(Integer... params) {
		return new MonthFragmentAdapter(this.context, this.getCalendarBeanList(DateTime.now().plusMonths(params[0] - MonthFragment.ITEM_COUNT / 2)));
	}

	@Override
	protected void onPostExecute(BaseAdapter result) {
		if (this.progressDialog != null) {
			this.progressDialog.cancel();
		}
		this.mGridView.setAdapter(result);
	}

	private List<CalendarBean> getCalendarBeanList(DateTime dt) {
		List<CalendarBean> list = new ArrayList<CalendarBean>();

		int currentMonth = dt.getMonthOfYear();

		dt = dt.withDayOfMonth(1);
		if (dt.getDayOfWeek() != DateTimeConstants.SUNDAY) {
			dt = dt.minusDays(dt.getDayOfWeek());
		}

		ChineseCalendar chineseCalendar = new ChineseCalendar(dt.toCalendar(null));
		ChineseCalendar cc = new ChineseCalendar(true, Integer.parseInt(chineseCalendar.getSimpleChineseDateString().substring(0, 4)), 12, 30);
		// 先调一下这个, 后面的getTime()才能生效
		cc.getSimpleGregorianDateString();
		// 除夕的公历日期
		CharSequence newYearEve = DateFormat.format("yyyyMMdd", cc.getTime()).toString();

		for (int i = 0; i < 42; i++) {
			CalendarBean bean = new CalendarBean();
			bean.setDay("" + dt.getDayOfMonth());
			chineseCalendar.set(dt.getYear(), dt.getMonthOfYear() - 1, dt.getDayOfMonth(), 0, 0, 0);

			String ymdStr = DateFormat.format("yyyyMMdd", dt.getMillis()).toString();
			String mdStr = ymdStr.substring(4);
			if (ymdStr.equals(newYearEve)) {
				bean.setChineseDay("除夕");
				bean.setFestival(true);
			} else if (Constants.FESTIVAL.containsKey(mdStr)) {
				bean.setChineseDay(Constants.FESTIVAL.get(mdStr));
				bean.setFestival(true);
			} else {
				String key = chineseCalendar.getChinese(ChineseCalendar.CHINESE_MONTH) + chineseCalendar.getChinese(ChineseCalendar.CHINESE_DATE);
				if (Constants.FESTIVAL.containsKey(key)) {
					bean.setChineseDay(Constants.FESTIVAL.get(key));
					bean.setFestival(true);
				}
			}
			if (bean.getChineseDay() == null) {
				String chineseDateName = chineseCalendar.getChinese(ChineseCalendar.CHINESE_TERM_OR_DATE);
				bean.setChineseDay(chineseDateName);
				bean.setFestival(ChineseCalendar.isTerm(chineseDateName));
			}

			// 判断是否为周末
			bean.setWeekend(dt.getDayOfWeek() == DateTimeConstants.SATURDAY || dt.getDayOfWeek() == DateTimeConstants.SUNDAY);

			// 判断日期状态
			if (Days.daysBetween(LocalDate.now(), dt.toLocalDate()).getDays() == 0) {
				bean.setDateState(1);
			} else if (currentMonth != dt.getMonthOfYear()) {
				bean.setDateState(0);
			} else {
				bean.setDateState(2);
			}

			list.add(bean);
			dt = dt.plusDays(1);
		}
		return list;
	}

}
