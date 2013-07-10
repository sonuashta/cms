package com.intut.luckylottery.cms.util;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;

import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.domain.Lottery;
import com.intut.luckylottery.cms.dummydata.GetDummyData;

public final class Util {

	public static String unWrapQuotes(String quotedString) {
		if (quotedString.startsWith("\"") && quotedString.endsWith("\""))
			return quotedString.substring(1, quotedString.length() - 1);
		return quotedString;
	}

	public static Rectangle setBouunds(int x, int y) {
		Rectangle r = new Rectangle((int) Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth()
				/ 2 - x / 2, 100, x, y);
		return r;
	}

	public static String getResultFormatedDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String result = dateFormat.format(date);
		return result;
	}

	public static String getPreviousDateString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.add(Calendar.HOUR, 0);
		calendar.add(Calendar.MINUTE, 0);
		calendar.add(Calendar.SECOND, 0);

		Date previousDate = calendar.getTime();
		String result = dateFormat.format(previousDate);

		return result;
	}

	public static String getNextDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(getTimeField(date, Calendar.YEAR),
				getTimeField(date, Calendar.MONTH),
				getTimeField(date, Calendar.DATE) + 1, 0, 0, 0);
		Date nextDate = calendar.getTime();
		String result = dateFormat.format(nextDate);

		return result;
	}

	public static String getOsName() {
		if (System.getProperty("os.name").contains("Windows"))
			return "windows";
		else if (System.getProperty("os.name").contains("Mac"))
			return "mac";
		else if (System.getProperty("os.name").contains("Linux"))
			return "Linux";
		else
			return "Unknown O.S";
	}

	public static Date getStartingdate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getTimeField(date, Calendar.YEAR),
				getTimeField(date, Calendar.MONTH),
				getTimeField(date, Calendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static Date getDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e) {
			Calendar cal = Calendar.getInstance();
			cal.set(1111, 11, 11);
			return cal.getTime();
		}
	}

	public static String[] getProcessSerialNumberCommands() {
		if (System.getProperty("os.name").contains("Windows"))
			return new String[] { "cmd.exe", "/c", "wmic csproduct get uuid" };
		else if (System.getProperty("os.name").contains("Mac"))
			return new String[] { "/bin/bash", "-c",
					"system_profiler |grep \"r (system\"" };

		else
			return null;
	}

	public static boolean isStringNullOrEmpty(String value) {
		return value == null ? true : value.trim().length() == 0 ? true : false;
	}

	public static String WrapQuotes(String value) {
		if (Util.isStringNullOrEmpty(value))
			return null;

		if (value.charAt(0) != '"')
			value = "\"" + value;

		if (value.charAt(value.length() - 1) != '"')
			value = value + "\"";

		return value;
	}

	public static String getRelativePath() {
		File file = new File("");
		return file.getAbsolutePath();
	}

	public static String getSMSUrl() {
		return "http://dndopen.dove-sms.com/TransSMS/SMSAPI.jsp";
	}

	public static String getUserName() {
		return "lucky1";
	}

	public static String getPassword() {
		return "lucky1";
	}

	public static String getSenderName() {
		return "LUCKYZ";
	}

	public static int getLotteryId(String type, String bumper) {
		for (Lottery lottery : GetDummyData.getLotteryData()) {
			if (lottery.getName().equals(bumper)
					&& lottery.getType().equalsIgnoreCase(type))
				return lottery.getId();
		}
		return 0;
	}

	private static int getTimeField(Date date, int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(field);
	}

	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getTimeField(date, Calendar.YEAR),
				getTimeField(date, Calendar.MONTH),
				getTimeField(date, Calendar.DATE) - 1, 0, 0, 0);
		return calendar.getTime();
	}
}
