package com.intut.luckylottery.cms.util;


import org.apache.log4j.Logger;

public class LotteryLogger {

	static final Logger logger = Logger.getLogger(LotteryLogger.class);
	private static LotteryLogger _instance;

	private LotteryLogger() {
	}

	public synchronized static LotteryLogger getInstance() {
		if (_instance == null)
			_instance = new LotteryLogger();

		return _instance;
	}

	public void setDebug(String message) {
		logger.debug(message);
	}

	public void setWarn(String message) {
		logger.warn(message);

	}

	public void setInfo(String message) {
		logger.info(message);
	}

	public void setError(String message) {

		logger.error(message);
	}

	public void setFatal(String message) {

		logger.fatal(message);
	}

}