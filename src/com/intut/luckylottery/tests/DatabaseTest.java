package com.intut.luckylottery.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.intut.luckylottery.crudDatabase.Dbloader;
import com.intut.luckylottery.domain.Customer;

public class DatabaseTest extends TestCase {

	private static Dbloader loader;

	@BeforeClass
	public void testSetup() {
		loader = new Dbloader();
	}

//	@s
//	public static void testCleanup() {
//
//		try {
//			loader.dropDatabase();
//		} catch (Exception e) {
//			Assert.fail("Failed to delete database");
//		}
//
//	}

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Test
	public void testInsertBackupEntries() {
		
		
		ReadExcel test = new ReadExcel();
		try {
			List<Customer> customers = test.read(new File(
					"C:\\Users\\KESHAV\\Desktop\\cms\\test.xlsx"), "Monthly",
					"");
			try {
				loader.insertbackupLotteries(customers);
				int count = loader.getCustomersCount();
				Assert.assertTrue(count == customers.size());

			} catch (Exception e) {
				Assert.fail(e.getMessage());
			}
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		}

	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		loader.dropDatabase();
	}

//	@Test
//	public void testCreateTable() {
//		// System.out.println("Im in test1");
//		// Dbloader loader = new Dbloader();
//		// loader.dropTables();
//		//
//		// loader.init();
//		// String processName = "Holi";
//		// loader.insertProcess(processName);
//		// loader.createMessageTable(processName);
//
//	}

}
