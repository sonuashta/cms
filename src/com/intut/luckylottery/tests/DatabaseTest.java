package com.intut.luckylottery.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.intut.luckylottery.crudDatabase.Dbloader;
import com.intut.luckylottery.domain.Customer;

public class DatabaseTest {

	@Test
	public void test() {
		System.out.println("Im in test");
		 Dbloader loader = new Dbloader();
		 loader.dropTables();
		 ReadExcel test = new ReadExcel();
		 try {
		 List<Customer> customers = test.read(new File(
		 "C:\\Users\\KESHAV\\Desktop\\cms\\test.xlsx"), "Monthly",
		 "");
		 File file = new File("");
		 System.out.println(file.getAbsolutePath());
		
		 loader.insertbackupLotteries(customers);
		 } catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }

	}

	@Test
	public void test1() {
//		System.out.println("Im in test1");
//		Dbloader loader = new Dbloader();
//		loader.dropTables();
//
//		loader.init();
//		String processName = "Holi";
//		loader.insertProcess(processName);
//		loader.createMessageTable(processName);

	}

}
