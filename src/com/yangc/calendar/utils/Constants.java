package com.yangc.calendar.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	/** 最大最小年份 */
	public static final int MIN_YEAR = 1902;
	public static final int MAX_YEAR = 2099;

	/** 开始结束日期 */
	public static final String BEGIN_DATE = "1901-12-31";
	public static final String END_DATE = "2100-01-01";
	public static final String BEGIN_MONTH = "1901-12";
	public static final String END_MONTH = "2100-01";

	public static int GV_HEIGHT = 0;

	public static boolean IS_EXIT = false;

	public static final Map<String, String> FESTIVAL = new HashMap<String, String>();

	static {
		FESTIVAL.put("0101", "元旦");
		FESTIVAL.put("0214", "情人节");
		FESTIVAL.put("0308", "妇女节");
		FESTIVAL.put("0401", "愚人节");
		FESTIVAL.put("0501", "劳动节");
		FESTIVAL.put("0504", "青年节");
		FESTIVAL.put("0601", "儿童节");
		FESTIVAL.put("0801", "建军节");
		FESTIVAL.put("0910", "教师节");
		FESTIVAL.put("1001", "国庆节");
		FESTIVAL.put("1031", "万圣节");
		FESTIVAL.put("1224", "平安夜");
		FESTIVAL.put("1225", "圣诞节");
		FESTIVAL.put("正月初一", "春节");
		FESTIVAL.put("正月十五", "元宵节");
		FESTIVAL.put("二月初二", "龙抬头");
		FESTIVAL.put("五月初五", "端午节");
		FESTIVAL.put("七月初七", "七夕");
		FESTIVAL.put("八月十五", "中秋节");
		FESTIVAL.put("九月初九", "重阳节");
		FESTIVAL.put("十二月初八", "腊八");
		FESTIVAL.put("十二月廿三", "小年");
	}

}
