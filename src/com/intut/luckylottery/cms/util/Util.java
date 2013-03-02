package com.intut.luckylottery.cms.util;

import java.awt.Toolkit;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.swt.graphics.Rectangle;

public final class Util {


	public static String unWrapQuotes(String quotedString) {
		if (quotedString.startsWith("\"") && quotedString.endsWith("\""))
			return quotedString.substring(1, quotedString.length() - 1);
		return quotedString;
	}
	

	public static Rectangle setBouunds(int x, int y) {
		Rectangle r = new Rectangle((int) Toolkit.getDefaultToolkit()
				.getScreenSize().getWidth()
				/ 2 - x / 2, 200, x, y);
		return r;
	}


	public static String formatDate(Date date) {
		return new SimpleDateFormat("dd MMM yyyy").format(date);
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
		return value == null ? true : value.length() == 0 ? true : false;
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
		return "9811090740";
	}


	public static String getPassword() {
		return "papa";
	}

}
