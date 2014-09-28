package com.yangc.calendar.bean;

public class CalendarBean {

	private String day;
	private String chineseDay;
	private int weekend; // 0:非周末, 1:周末
	private int dateState; // 0:非当月, 1:今天, 2:当月但非今天
	private int festival; // 0:非节日, 1:节日

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

	public int getWeekend() {
		return weekend;
	}

	public void setWeekend(int weekend) {
		this.weekend = weekend;
	}

	public int getDateState() {
		return dateState;
	}

	public void setDateState(int dateState) {
		this.dateState = dateState;
	}

	public int getFestival() {
		return festival;
	}

	public void setFestival(int festival) {
		this.festival = festival;
	}

}
