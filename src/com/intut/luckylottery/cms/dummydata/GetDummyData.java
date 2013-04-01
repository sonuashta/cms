package com.intut.luckylottery.cms.dummydata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.intut.luckylottery.cms.crudDatabase.Dbloader;
import com.intut.luckylottery.cms.domain.Lottery;

public class GetDummyData {

	public static List<Lottery> getLotteryData() {
		List<Lottery> list = new ArrayList<Lottery>();
		Lottery type = new Lottery();
		type.setId(1);
		type.setName("Monthly");
		type.setType("Monthly");
		list.add(type);
		type = new Lottery();
		type.setId(2);
		type.setType("Bumper");
		type.setName("Diwali");
		list.add(type);
		type = new Lottery();
		type.setId(3);
		type.setType("Bumper");
		type.setName("Holi");
		list.add(type);
		return list;
	}

	public static String getSelectedBumper() {
		Dbloader lloder = new Dbloader();
		try {
			
			return lloder.getSelectedBumper();
		} catch (Exception e) {
			return "";
		}
	}

	public static String[] getBumperNames() {
		Dbloader lloder = new Dbloader();
		try {
			List<String> list = lloder.getBumpers();
			String[] bumpers = list.toArray(new String[list.size()]);
			return bumpers;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String[] { "" };
	}

	public static int getSerialNumber() {
		return 123456;
	}
}
