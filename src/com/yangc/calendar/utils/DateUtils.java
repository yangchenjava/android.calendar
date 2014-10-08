package com.yangc.calendar.utils;

import java.util.Calendar;

public class DateUtils {

	private DateUtils() {
	}

	/**
	 * @功能: 获取相差月数
	 * @作者: yangc
	 * @创建日期: 2013-11-12 下午03:00:30
	 */
	public static int getOffsetMonths(Calendar c1, Calendar c2) {
		int i = 0;
		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR) || c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) {
			if (c1.getTimeInMillis() > c2.getTimeInMillis()) {
				while (true) {
					i--;
					c1.add(Calendar.MONTH, -1);
					if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
						break;
					}
				}
			} else {
				while (true) {
					i++;
					c1.add(Calendar.MONTH, 1);
					if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {
						break;
					}
				}
			}
		}
		return i;
	}

	/**
	 * @功能: 获取相差天数
	 * @作者: yangc
	 * @创建日期: 2013-11-12 下午03:00:30
	 */
	public static int getOffsetDays(Calendar c1, Calendar c2) {
		int i = 0;
		if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR) || c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH) || c1.get(Calendar.DATE) != c2.get(Calendar.DATE)) {
			if (c1.getTimeInMillis() > c2.getTimeInMillis()) {
				while (true) {
					i--;
					c1.add(Calendar.DATE, -1);
					if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE)) {
						break;
					}
				}
			} else {
				while (true) {
					i++;
					c1.add(Calendar.DATE, 1);
					if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE)) {
						break;
					}
				}
			}
		}
		return i;
	}

	/**
	 * @功能: 判断是否为今天
	 * @作者: yangc
	 * @创建日期: 2014年9月29日 下午4:21:42
	 * @param milliseconds
	 * @return
	 */
	public static boolean isToday(long milliseconds) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(milliseconds);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTimeInMillis(System.currentTimeMillis());
		return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
	}

}
