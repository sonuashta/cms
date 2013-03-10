package com.intut.luckylottery.tests;

import java.util.ArrayList;
import java.util.List;

import com.intut.luckylottery.domain.Customer;

public class LotteryCustomer {

	private List<Customer> customerList;

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}

	public LotteryCustomer() {
		setCustomerList(new ArrayList<Customer>());
	}
}
