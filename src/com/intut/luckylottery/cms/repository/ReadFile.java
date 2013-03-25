package com.intut.luckylottery.cms.repository;

import java.util.ArrayList;
import java.util.List;

import com.intut.luckylottery.cms.domain.NewCustomer;
import com.intut.luckylottery.cms.util.Util;

public class ReadFile {

	public List<NewCustomer> getCustomersFrom2007File(String bumperType,
			String monthlyType) {

		List<NewCustomer> customers = new ArrayList<NewCustomer>();
		NewCustomer customer = new NewCustomer();
		customer.setName("Hit");
		customer.setPhoneNumber("12");
		customer.setEmailId("h@gmail.com");
		customer.setBumperIds("1234");
		customers.add(customer);

		customer = new NewCustomer();
		customer.setName("Hit");
		customer.setPhoneNumber("12");
		customer.setBumperIds("321");
		customer.setEmailId("h@gmail.com");
		addToListIfNotExist(customers, customer);

		customer = new NewCustomer();
		customer.setName("Raman");
		customer.setPhoneNumber("12");
		customer.setBumperIds("1234");
		customer.setEmailId("h@gmail.com");
		
		addToListIfNotExist(customers, customer);
		return customers;
	}

	private void addToListIfNotExist(List<NewCustomer> customers,
			NewCustomer customer) {
		boolean isExist = false;
		for (NewCustomer cust : customers) {
			if (!Util.isStringNullOrEmpty(cust.getName())
					&& cust.getName().equalsIgnoreCase(
							customer.getName().trim())
					&& (cust.getEmailId().equalsIgnoreCase(
							customer.getEmailId().trim()) || cust
							.getPhoneNumber().equals(
									customer.getPhoneNumber().trim()))) {
				isExist = true;
				cust.setBumperIds(cust.getBumperIds() + ","
						+ customer.getBumperIds());
			}
		}
		if (!isExist)
			customers.add(customer);
	}
	
	public static void main(String a[]){
		ReadFile file=new ReadFile();
		file.getCustomersFrom2007File("", "");
		
	}
	
}
