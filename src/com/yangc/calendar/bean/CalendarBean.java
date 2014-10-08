package com.yangc.calendar.bean;

public class CalendarBean {

	private String day;
	private String chineseDay;
	private boolean weekend; // 周末
	private int dateState; // 0:非当月, 1:今天, 2:当月但非今天
	private boolean festival; // 节日

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getChineseDay() {
		return chineseDay;
	}

	public void setChineseDay(String chineseDay) {
		this.chineseDay = chineseDay;
	}

	public boolean isWeekend() {
		return weekend;
	}

	public void setWeekend(boolean weekend) {
		this.weekend = weekend;
	}

	public int getDateState() {
		return dateState;
	}

	public void setDateState(int dateState) {
		this.dateState = dateState;
	}

	public boolean isFestival() {
		return festival;
	}

	public void setFestival(boolean festival) {
		this.festival = festival;
	}

}
