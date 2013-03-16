package com.intut.luckylottery.tests;

public class ExceptionTest {

	public static void main(String a[]) {
		try {
			ExceptionTest.test();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void test() throws Exception {
		try {
			throw new Exception();
		} catch (Exception e) {
			System.out.println("Incatch");
			throw new Exception("Exception text");

		} finally {
			System.out.println("In finally");
		}
	}
}
