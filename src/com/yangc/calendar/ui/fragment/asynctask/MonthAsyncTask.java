package com.yangc.calendar.ui.fragment.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.yangc.calendar.R;
import com.yangc.calendar.bean.CalendarBean;
import com.yangc.calendar.ui.fragment.MonthFragment;
import com.yangc.calendar.ui.fragment.adapter.MonthFragmentAdapter;
import com.yangc.calendar.utils.AndroidUtils;
import com.yangc.calendar.utils.ChineseCalendar;
import com.yangc.calendar.utils.Constants;

public class MonthAsyncTask extends AsyncTask<Integer, Integer, BaseAdapter> {

	private Context context;
	private GridView gridView;
	private Dialog progressDialog;

	public MonthAsyncTask(Context context, GridView gridView) {
		this.context = context;
		this.gridView = gridView;
	}

	@Override
	protected void onPreExecute() {
		if (MonthFragment.ONCE.compareAndSet(true, false)) {
			this.progressDialog = AndroidUtils.showProgressDialog(this.context, this.context.getResources().getString(R.string.text_load), true, true);
		}
	}

	@Override
	protected BaseAdapter doInBackground(Integer... params) {
		// 延迟进度旋转,美化交互
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new MonthFragmentAdapter(this.context, this.getCalendarBeanList(LocalDate.now().plusMonths(params[0])));
	}

	@Override
	protected void onPostExecute(BaseAdapter result) {
		if (this.progressDialog != null) {
			this.progressDialog.dismiss();
		}
		this.gridView.setAdapter(result);
	}

	private List<CalendarBean> getCalendarBeanList(LocalDate ld) {
		List<CalendarBean> list = new ArrayList<CalendarBean>();

		int currentMonth = ld.getMonthOfYear();

		ld = ld.withDayOfMonth(1);
		if (ld.getDayOfWeek() != DateTimeConstants.SUNDAY) {
			ld = ld.minusDays(ld.getDayOfWeek());
		}

		ChineseCalendar chineseCalendar = new ChineseCalendar(ld.toDateTimeAtStartOfDay().toCalendar(null));
		ChineseCalendar cc = new ChineseCalendar(true, Integer.parseInt(chineseCalendar.getSimpleChineseDateString().substring(0, 4)), 12, 30);
		// 先调一下这个, 后面的getTime()才能生效
		cc.getSimpleGregorianDateString();
		// 除夕的公历日期
		String newYearEve = DateTimeFormat.forPattern("yyyyMMdd").print(cc.getTimeInMillis());

		for (int i = 0; i < 42; i++) {
			CalendarBean bean = new CalendarBean();
			bean.setDay("" + ld.getDayOfMonth());
			chineseCalendar.set(ld.getYear(), ld.getMonthOfYear() - 1, ld.getDayOfMonth(), 0, 0, 0);

			String ymdStr = ld.toString("yyyyMMdd");
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
			bean.setWeekend(ld.getDayOfWeek() == DateTimeConstants.SATURDAY || ld.getDayOfWeek() == DateTimeConstants.SUNDAY);

			// 判断日期状态
			if (Days.daysBetween(LocalDate.now(), ld).getDays() == 0) {
				bean.setDateState(1);
			} else if (currentMonth != ld.getMonthOfYear()) {
				bean.setDateState(0);
			} else {
				bean.setDateState(2);
			}

			list.add(bean);
			ld = ld.plusDays(1);
		}
		return list;
	}

}
