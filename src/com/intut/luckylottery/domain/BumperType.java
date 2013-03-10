package com.intut.luckylottery.domain;

import java.util.ArrayList;
import java.util.List;

public class BumperType {

	private static List<String> bumpers;

	public static List<String> getBumpers() {
		if (bumpers == null) {
			bumpers = new ArrayList<String>();
			bumpers.add("Diwali");
			bumpers.add("Holi");
		}
		return bumpers;
	}
}
