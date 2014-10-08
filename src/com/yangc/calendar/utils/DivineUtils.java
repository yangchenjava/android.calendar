package com.yangc.calendar.utils;

import android.content.Context;
import android.widget.TextView;

import com.yangc.calendar.R;

public class DivineUtils {

	public static void setGoodBad(TextView good1, TextView goodContent1, TextView good2, TextView goodContent2, TextView bad1, TextView badContent1, TextView bad2, TextView badContent2, int day) {
		int a = day % 16 + 1;
		int b = (day + 2) % 16 + 1;
		int c = (day + 4) % 16 + 1;
		int d = (day + 6) % 16 + 1;

		switch (a) {
		case 1:
			good1.setText(R.string.title_1);
			goodContent1.setText(R.string.good_content_1);
			break;
		case 2:
			good1.setText(R.string.title_2);
			goodContent1.setText(R.string.good_content_2);
			break;
		case 3:
			good1.setText(R.string.title_3);
			goodContent1.setText(R.string.good_content_3);
			break;
		case 4:
			good1.setText(R.string.title_4);
			goodContent1.setText(R.string.good_content_4);
			break;
		case 5:
			good1.setText(R.string.title_5);
			goodContent1.setText(R.string.good_content_5);
			break;
		case 6:
			good1.setText(R.string.title_6);
			goodContent1.setText(R.string.good_content_6);
			break;
		case 7:
			good1.setText(R.string.title_7);
			goodContent1.setText(R.string.good_content_7);
			break;
		case 8:
			good1.setText(R.string.title_8);
			goodContent1.setText(R.string.good_content_8);
			break;
		case 9:
			good1.setText(R.string.title_9);
			goodContent1.setText(R.string.good_content_9);
			break;
		case 10:
			good1.setText(R.string.title_10);
			goodContent1.setText(R.string.good_content_10);
			break;
		case 11:
			good1.setText(R.string.title_11);
			goodContent1.setText(R.string.good_content_11);
			break;
		case 12:
			good1.setText(R.string.title_12);
			goodContent1.setText(R.string.good_content_12);
			break;
		case 13:
			good1.setText(R.string.title_13);
			goodContent1.setText(R.string.good_content_13);
			break;
		case 14:
			good1.setText(R.string.title_14);
			goodContent1.setText(R.string.good_content_14);
			break;
		case 15:
			good1.setText(R.string.title_15);
			goodContent1.setText(R.string.good_content_15);
			break;
		case 16:
			good1.setText(R.string.title_16);
			goodContent1.setText(R.string.good_content_16);
			break;
		}

		switch (b) {
		case 1:
			good2.setText(R.string.title_1);
			goodContent2.setText(R.string.good_content_1);
			break;
		case 2:
			good2.setText(R.string.title_2);
			goodContent2.setText(R.string.good_content_2);
			break;
		case 3:
			good2.setText(R.string.title_3);
			goodContent2.setText(R.string.good_content_3);
			break;
		case 4:
			good2.setText(R.string.title_4);
			goodContent2.setText(R.string.good_content_4);
			break;
		case 5:
			good2.setText(R.string.title_5);
			goodContent2.setText(R.string.good_content_5);
			break;
		case 6:
			good2.setText(R.string.title_6);
			goodContent2.setText(R.string.good_content_6);
			break;
		case 7:
			good2.setText(R.string.title_7);
			goodContent2.setText(R.string.good_content_7);
			break;
		case 8:
			good2.setText(R.string.title_8);
			goodContent2.setText(R.string.good_content_8);
			break;
		case 9:
			good2.setText(R.string.title_9);
			goodContent2.setText(R.string.good_content_9);
			break;
		case 10:
			good2.setText(R.string.title_10);
			goodContent2.setText(R.string.good_content_10);
			break;
		case 11:
			good2.setText(R.string.title_11);
			goodContent2.setText(R.string.good_content_11);
			break;
		case 12:
			good2.setText(R.string.title_12);
			goodContent2.setText(R.string.good_content_12);
			break;
		case 13:
			good2.setText(R.string.title_13);
			goodContent2.setText(R.string.good_content_13);
			break;
		case 14:
			good2.setText(R.string.title_14);
			goodContent2.setText(R.string.good_content_14);
			break;
		case 15:
			good2.setText(R.string.title_15);
			goodContent2.setText(R.string.good_content_15);
			break;
		case 16:
			good2.setText(R.string.title_16);
			goodContent2.setText(R.string.good_content_16);
			break;
		}

		switch (c) {
		case 1:
			bad1.setText(R.string.title_1);
			badContent1.setText(R.string.bad_content_1);
			break;
		case 2:
			bad1.setText(R.string.title_2);
			badContent1.setText(R.string.bad_content_2);
			break;
		case 3:
			bad1.setText(R.string.title_3);
			badContent1.setText(R.string.bad_content_3);
			break;
		case 4:
			bad1.setText(R.string.title_4);
			badContent1.setText(R.string.bad_content_4);
			break;
		case 5:
			bad1.setText(R.string.title_5);
			badContent1.setText(R.string.bad_content_5);
			break;
		case 6:
			bad1.setText(R.string.title_6);
			badContent1.setText(R.string.bad_content_6);
			break;
		case 7:
			bad1.setText(R.string.title_7);
			badContent1.setText(R.string.bad_content_7);
			break;
		case 8:
			bad1.setText(R.string.title_8);
			badContent1.setText(R.string.bad_content_8);
			break;
		case 9:
			bad1.setText(R.string.title_9);
			badContent1.setText(R.string.bad_content_9);
			break;
		case 10:
			bad1.setText(R.string.title_10);
			badContent1.setText(R.string.bad_content_10);
			break;
		case 11:
			bad1.setText(R.string.title_11);
			badContent1.setText(R.string.bad_content_11);
			break;
		case 12:
			bad1.setText(R.string.title_12);
			badContent1.setText(R.string.bad_content_12);
			break;
		case 13:
			bad1.setText(R.string.title_13);
			badContent1.setText(R.string.bad_content_13);
			break;
		case 14:
			bad1.setText(R.string.title_14);
			badContent1.setText(R.string.bad_content_14);
			break;
		case 15:
			bad1.setText(R.string.title_15);
			badContent1.setText(R.string.bad_content_15);
			break;
		case 16:
			bad1.setText(R.string.title_16);
			badContent1.setText(R.string.bad_content_16);
			break;
		}

		switch (d) {
		case 1:
			bad2.setText(R.string.title_1);
			badContent2.setText(R.string.bad_content_1);
			break;
		case 2:
			bad2.setText(R.string.title_2);
			badContent2.setText(R.string.bad_content_2);
			break;
		case 3:
			bad2.setText(R.string.title_3);
			badContent2.setText(R.string.bad_content_3);
			break;
		case 4:
			bad2.setText(R.string.title_4);
			badContent2.setText(R.string.bad_content_4);
			break;
		case 5:
			bad2.setText(R.string.title_5);
			badContent2.setText(R.string.bad_content_5);
			break;
		case 6:
			bad2.setText(R.string.title_6);
			badContent2.setText(R.string.bad_content_6);
			break;
		case 7:
			bad2.setText(R.string.title_7);
			badContent2.setText(R.string.bad_content_7);
			break;
		case 8:
			bad2.setText(R.string.title_8);
			badContent2.setText(R.string.bad_content_8);
			break;
		case 9:
			bad2.setText(R.string.title_9);
			badContent2.setText(R.string.bad_content_9);
			break;
		case 10:
			bad2.setText(R.string.title_10);
			badContent2.setText(R.string.bad_content_10);
			break;
		case 11:
			bad2.setText(R.string.title_11);
			badContent2.setText(R.string.bad_content_11);
			break;
		case 12:
			bad2.setText(R.string.title_12);
			badContent2.setText(R.string.bad_content_12);
			break;
		case 13:
			bad2.setText(R.string.title_13);
			badContent2.setText(R.string.bad_content_13);
			break;
		case 14:
			bad2.setText(R.string.title_14);
			badContent2.setText(R.string.bad_content_14);
			break;
		case 15:
			bad2.setText(R.string.title_15);
			badContent2.setText(R.string.bad_content_15);
			break;
		case 16:
			bad2.setText(R.string.title_16);
			badContent2.setText(R.string.bad_content_16);
			break;
		}
	}

	public static void setFace(Context context, TextView face, int day) {
		int a = (day / 10 + day % 10) % 8;
		String value = "";
		switch (a) {
		case 0:
			value = context.getString(R.string.face_0);
			break;
		case 1:
			value = context.getString(R.string.face_1);
			break;
		case 2:
			value = context.getString(R.string.face_2);
			break;
		case 3:
			value = context.getString(R.string.face_3);
			break;
		case 4:
			value = context.getString(R.string.face_4);
			break;
		case 5:
			value = context.getString(R.string.face_5);
			break;
		case 6:
			value = context.getString(R.string.face_6);
			break;
		case 7:
			value = context.getString(R.string.face_7);
			break;
		}
		face.setText("座位朝向 : 面向" + value + "写程序 , BUG最少");
	}

	public static void setDrink(Context context, TextView drink, int day) {
		int a = day / 10;
		int b = day % 10;
		String value = "";
		switch (a) {
		case 0:
			value = context.getString(R.string.drink_0);
			break;
		case 1:
			value = context.getString(R.string.drink_1);
			break;
		case 2:
			value = context.getString(R.string.drink_2);
			break;
		case 3:
			value = context.getString(R.string.drink_3);
			break;
		case 4:
			value = context.getString(R.string.drink_4);
			break;
		case 5:
			value = context.getString(R.string.drink_5);
			break;
		case 6:
			value = context.getString(R.string.drink_6);
			break;
		case 7:
			value = context.getString(R.string.drink_7);
			break;
		case 8:
			value = context.getString(R.string.drink_8);
			break;
		case 9:
			value = context.getString(R.string.drink_9);
			break;
		}
		if (a != b) {
			switch (b) {
			case 0:
				value += " , " + context.getString(R.string.drink_0);
				break;
			case 1:
				value += " , " + context.getString(R.string.drink_1);
				break;
			case 2:
				value += " , " + context.getString(R.string.drink_2);
				break;
			case 3:
				value += " , " + context.getString(R.string.drink_3);
				break;
			case 4:
				value += " , " + context.getString(R.string.drink_4);
				break;
			case 5:
				value += " , " + context.getString(R.string.drink_5);
				break;
			case 6:
				value += " , " + context.getString(R.string.drink_6);
				break;
			case 7:
				value += " , " + context.getString(R.string.drink_7);
				break;
			case 8:
				value += " , " + context.getString(R.string.drink_8);
				break;
			case 9:
				value += " , " + context.getString(R.string.drink_9);
				break;
			}
		}
		drink.setText("今日宜饮 : " + value);
	}

	public static void setGirl(TextView girl, int year, int month, int day) {
		double value = year * month * day % 50 / 10.0;
		if (value < 5) {
			value += 4;
		}
		girl.setText("女神亲近指数 : " + value);
	}

}
