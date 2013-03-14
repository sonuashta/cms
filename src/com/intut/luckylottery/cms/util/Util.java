package com.intut.luckylottery.cms.util;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.graphics.Rectangle;

import com.intut.luckylottery.domain.Customer;
import com.intut.luckylottery.domain.Lottery;

import dummydata.GetDummyData;

public final class Util {

	public static String unWrapQuotes(String quotedString) {
		if (quotedString.startsWith("\"") && quotedString.endsWith("\""))
			return quotedString.substring(1, quotedString.length() - 1);
		return quotedString;
	}

	public static Rectangle setBouunds(int x, int y) {
		Rectangle r = new Rectangle((int) Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth()
				/ 2 - x / 2, 150, x, y);
		return r;
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

	public static String formatDate(Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static Date getDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
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

	public static String getUserName() {
		return "lucky1";
	}

	public static String getPassword() {
		return "lucky1";
	}

	public static String getSenderName(){
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

}
