package com.intut.luckylottery.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.intut.luckylottery.crudDatabase.Dbloader;
import com.intut.luckylottery.domain.Customer;

public class DatabaseTest {

	@Test
	public void test() {
		Dbloader loader = new Dbloader();
		Customer customer = new Customer();
		customer.setAddress("8/39");
		customer.setCity("Delhi");
		customer.setCode("12989");
		customer.setCreatedDate(new Date());
		customer.setDeletedDate(null);
		customer.setEmail("k@gmail.com");
		customer.setLotterTypeId(1);
		customer.setMobile1("9213");
		customer.setMobile2("98110");
		customer.setName("keshav");
		customer.setSmsSend(false);
		customer.setState("New Delhi");
		customer.setZip("110065");
		loader.insertCustomer(customer);
	}

}
