package com.intut.luckylottery.cms.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SmsCredential {

	private static SmsCredential _instance;
	private String url;
	private String messageKey;
	private String mobileKey;
	private String otherParams;

	private SmsCredential() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("smsCredentials.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			String lines[] = everything.split(System.lineSeparator());
			setUrl(lines[0].trim());
			setMessageKey(lines[1].trim());
			setMobileKey(lines[2].trim());
			setOtherParams(lines[3].trim());


		} finally {
			br.close();
		}
	}

	public synchronized static SmsCredential getInstance() throws IOException {
		if (_instance == null)
			_instance = new SmsCredential();

		return _instance;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public void setMessageKey(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMobileKey() {
		return mobileKey;
	}

	public void setMobileKey(String mobileKey) {
		this.mobileKey = mobileKey;
	}

	public String getOtherParams() {
		return otherParams;
	}

	public void setOtherParams(String otherParams) {
		this.otherParams = otherParams;
	}

}
