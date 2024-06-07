package com.hb.api.ms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hb.api.ms.constants.AppConstants;

public class DateUtils {

	private static final ThreadLocal<SimpleDateFormat> format = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
			dateFormat.setLenient(false);
			return dateFormat;
		}
	};

	public static Date getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MILLISECOND, 00);
		return calendar.getTime();
	}

	public static Date formatteDate(String date) throws ParseException {
		return format.get().parse(date);
	}
}
