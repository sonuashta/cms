package com.intut.luckylottery.cms.tests;

import junit.framework.TestCase;

import org.junit.Test;

import com.intut.luckylottery.cms.crudDatabase.Dbloader;

public class DatabaseTest extends TestCase {

	// private static Dbloader loader;
	//
	// @BeforeClass
	// public void testSetup() {
	// loader = new Dbloader();
	//
	// }

	// @s
	// public static void testCleanup() {
	//
	// try {
	// loader.dropDatabase();
	// } catch (Exception e) {
	// Assert.fail("Failed to delete database");
	// }
	//
	// }

//	@Test
//	public void testInsertBackupEntries() {
//		Dbloader loader = new Dbloader();
//		try {
//			loader.dropDatabase();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ReadExcel test = new ReadExcel();
//		try {
//			List<Customer> customers = test.read(new File(
//					"C:\\Users\\KESHAV\\Desktop\\cms\\test.xlsx"), "Monthly",
//					"");
//			try {
//				loader.insertbackupLotteries(customers);
//				int count = loader.getCustomersCount();
//				Assert.assertTrue(count == customers.size());
//
//			} catch (Exception e) {
//				Assert.fail(e.getMessage());
//			}
//		} catch (IOException e) {
//			Assert.fail(e.getMessage());
//		}
//
//	}
//
//	// @Override
//	// protected void tearDown() throws Exception {
//	// // TODO Auto-generated method stub
//	// super.tearDown();
//	// loader.dropDatabase();
//	// }
//	@Test
//	public void testGetFilteredCustomer() {
//		Dbloader loader = new Dbloader();
//
//		try {
//			// loader.dropDatabase();
//			ReadExcel test = new ReadExcel();
//			// loader.dropDatabase();
//			List<Customer> excelCustomers = test.read(new File(
//					"C:\\Users\\KESHAV\\Desktop\\cms\\test.xlsx"), "Monthly",
//					"");
//			try {
//				// loader.insertbackupLotteries(excelCustomers);
//				int count = loader.getCustomersCount();
//				Assert.assertTrue(count == excelCustomers.size());
//
//			} catch (Exception e) {
//				Assert.fail(e.getMessage());
//			}
//
////			List<Customer> customers = loader.getCustomers("hitanshu",
////					Util.getPreviousDate(new Date()), new Date(), "monthly",
////					"", "", "", "", false, "", false, 0, 10);
////			int count = customers.size();
////			Assert.assertTrue(count == 1);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testPreviousDate() {
		
		Dbloader loader = new Dbloader();

		try {
			loader.mergeDatabase("lottery", "new");
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

}
