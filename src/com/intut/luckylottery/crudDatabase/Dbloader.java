package com.intut.luckylottery.crudDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.domain.Customer;
import com.intut.luckylottery.domain.Lottery;


public class Dbloader {

	private Connection getConnection() {
		Connection connection;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager
					.getConnection("jdbc:sqlite:lottery.db");
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void dropTables() {
		try {

			Connection conn = getConnection();

			Statement stat = conn.createStatement();
			stat.executeUpdate("drop table if exists products;");
			stat.executeUpdate("drop table if exists invoiceDetails;");
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public boolean init() {
		try {

			Connection conn = getConnection();
			Statement stat = conn.createStatement();

			stat.executeUpdate("create table if not exists customer( id INTEGER PRIMARY KEY AUTOINCREMENT," +
					"name TEXT," +
					"code TEXT," +
					"mobile1 TEXT," +
					" mobile2 TEXT," +
					"address TEXT," +
					" city TEXT ," +
					" state TEXT," +
					"zip TEXT ," +
					"email TEXT," +
					"lotteryTypeId INTEGER," +
					"isMessageSend Numeric," +
					"date date," +
					"deletedDate date);");
			stat.executeUpdate("create table if not exists lotteryType(" +
					" id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT,name TEXT);");
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

//
// public void deleteProducts(int keycode) {
// try {
//
// Connection conn = getConnection();
// Statement stat = conn.createStatement();
//
// stat.executeUpdate("delete from products where keycode =" + keycode);
// stat.close();
// conn.close();
//
// } catch (Exception e) {
// e.printStackTrace();
//
// }
// }
// public void deleteInvoice(int keycode) {
// try {
//
// Connection conn = getConnection();
// Statement stat = conn.createStatement();
//
// stat.executeUpdate("delete from invoiceDetails where id =" + keycode);
// stat.close();
// conn.close();
//
// } catch (Exception e) {
// e.printStackTrace();
//
// }
//
// }
//
	public boolean insertCustomer(Customer customer) {
		 try {
		 init();
		 Connection conn = getConnection();

		 PreparedStatement prep = conn
							.prepareStatement("insert into customer values ( null,"
									+
							"?," +
							"?," +
							"?," +
							" ?," +
							"?," +
							" ?," +
							"?," +
							"? ," +
							"?," +
							"?," +
							"?," +
							"?," +
							"?);");

		 prep.setString(1, customer.getName());
		 prep.setString(2, customer.getCode());
		 prep.setString(3, customer.getMobile1());
		 prep.setString(4, customer.getMobile2());
		 prep.setString(5, customer.getAddress());
		 prep.setString(6, customer.getCity());
		 prep.setString(7, customer.getState());
		 prep.setString(8, customer.getZip());
		 prep.setString(9, customer.getEmail());
		 prep.setInt(10, customer.getLotterTypeId());
		 prep.setBoolean(11, customer.isSmsSend());
		 prep.setString(12, formatDate(customer.getCreatedDate()));
		 prep.setString(11, formatDate(customer.getDeletedDate()));
		 
		 prep.addBatch();
		 conn.setAutoCommit(false);
		 prep.executeBatch();
		 conn.setAutoCommit(true);
		 prep.close();
		 conn.close();

		 } catch (Exception e) {
		 e.printStackTrace();
		 return false;
		 }
		 return true;

		 }
	
	public boolean insertLottery(Lottery lotteryType) {
		 try {
		 init();
		 Connection conn = getConnection();

		 PreparedStatement prep = conn
							.prepareStatement("insert into customer values ( null,"
									+
							"?," +
							"? );");

		 prep.setString(1, lotteryType.getType());
		 prep.setString(2, lotteryType.getName());
		 prep.addBatch();
		 conn.setAutoCommit(false);
		 prep.executeBatch();
		 conn.setAutoCommit(true);
		 prep.close();
		 conn.close();

		 } catch (Exception e) {
		 e.printStackTrace();
		 return false;
		 }
		 return true;

		 }
 
	
	
// public boolean insertProduct(List<Product> products) {
//
// try {
// init();
// Connection conn = getConnection();
//
// for (Product product : products) {
// PreparedStatement prep = conn
// .prepareStatement("insert into products values ( null,  ?, ?, ?, ?, ?);");
//
// prep.setString(1, product.getKeycode());
// prep.setString(2, product.getDescription());
// prep.setInt(3, product.getQuantity());
//
// prep.setDouble(4, product.getPrice());
// prep.setDouble(5, product.getTax());
// prep.addBatch();
// conn.setAutoCommit(false);
// prep.executeBatch();
// conn.setAutoCommit(true);
// prep.close();
// }
//
// conn.close();
//
// } catch (Exception e) {
// e.printStackTrace();
// return false;
// }
// return true;
// }
//
 public String formatDate(Date date) {
	 if(date==null)
		 return "";
 return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
 }
//}


//
 public List<Lottery> getLotteryMap() {

 try {
 init();
 Connection conn = getConnection();
 Statement stat = conn.createStatement();
 ResultSet rs = stat
 .executeQuery("select distinct name from lotteryType");
 List<Lottery> lotteryTypeList=new ArrayList<Lottery>();
 
 while (rs.next()) {
	 Lottery type =new Lottery();
	 type.setId(rs.getInt("id"));
 String key = rs.getString("type") == null ? "" : rs
 .getString("type");
 String value = rs.getString("name") == null ? "" : rs
		 .getString("name");
 
 type.setName(key);
 type.setType(value);
 lotteryTypeList.add(type);
 }

 rs.close();

 conn.setAutoCommit(true);
 conn.close();
 return lotteryTypeList;
 } catch (Exception e) {
LotteryLogger.getInstance().setError("Error in fetching lottery type due to :"+e.getMessage());
 return null;
 }}

 }
//
// public List<Invoice> getInvoiceDetails(Date date) {
//
// try {
// init();
// Connection conn = getConnection();
// Statement stat = conn.createStatement();
// ResultSet rs = stat
// .executeQuery("select * from invoiceDetails where date="
// + "'" + formatDate(date) + "'");
// List<Invoice> list = new ArrayList<Invoice>();
// while (rs.next()) {
// Invoice invoice = new Invoice();
//
// invoice.setComments(rs.getString("comments") == null ? "" : rs
// .getString("comments"));
// invoice.setCustomerAddress(rs.getString("customerAddress") == null ? ""
// : rs.getString("customerAddress"));
// invoice.setCustomerName(rs.getString("customerName") == null ? ""
// : rs.getString("customerName"));
// invoice.setCustomerPhoneNumber(rs
// .getString("customerPhoneNumber") == null ? "" : rs
// .getString("customerPhoneNumber"));
// invoice.setInvoiceNumber(rs.getInt("id"));
// invoice.setSalesPerson(rs.getString("salesPerson") == null ? ""
// : rs.getString("salesPerson"));
// invoice.setSubTotal(rs.getDouble("subTotal"));
// invoice.setTotal(rs.getDouble("total"));
// invoice.setDate(rs.getString("date") == null ? getDate("")
// : getDate(rs.getString("date")));
// list.add(invoice);
// }
//
// rs.close();
//
// conn.setAutoCommit(true);
// conn.close();
// return list;
// } catch (Exception e) {
// e.printStackTrace();
// return null;
// }
//
// }
//
// public Invoice getInvoice(String keycode) {
//
// try {
// init();
// Connection conn = getConnection();
// Statement stat = conn.createStatement();
// ResultSet rs = stat
// .executeQuery("select * from invoiceDetails where id="
// + "'" + keycode + "'");
// Invoice invoice = new Invoice();
// while (rs.next()) {
//
// invoice.setComments(rs.getString("comments") == null ? "" : rs
// .getString("comments"));
// invoice.setCustomerAddress(rs.getString("customerAddress") == null ? ""
// : rs.getString("customerAddress"));
// invoice.setCustomerName(rs.getString("customerName") == null ? ""
// : rs.getString("customerName"));
// invoice.setCustomerPhoneNumber(rs
// .getString("customerPhoneNumber") == null ? "" : rs
// .getString("customerPhoneNumber"));
// invoice.setInvoiceNumber(rs.getInt("id"));
// invoice.setSalesPerson(rs.getString("salesPerson") == null ? ""
// : rs.getString("salesPerson"));
// invoice.setSubTotal(rs.getDouble("subTotal"));
// invoice.setTotal(rs.getDouble("total"));
// invoice.setDate(rs.getString("date") == null ? getDate("")
// : getDate(rs.getString("date")));
//
// }
//
// rs.close();
//
// conn.setAutoCommit(true);
// conn.close();
// return invoice;
// } catch (Exception e) {
// e.printStackTrace();
// return null;
// }
//
// }
//
// private Date getDate(String date) {
// try {
// return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(date);
// } catch (ParseException e) {
// Calendar cal = Calendar.getInstance();
// cal.set(1111, 11, 11);
// return cal.getTime();
// }
// }
//
// public int getUniqueInvoiceNumber() {
// int i = 0;
// try {
// init();
// Connection conn = getConnection();
// Statement stat = conn.createStatement();
// ResultSet rs = stat
// .executeQuery("select max(id) as id from invoiceDetails");
//
// while (rs.next()) {
// i = rs.getInt("id");
//
// }
//
// rs.close();
//
// conn.setAutoCommit(true);
// conn.close();
// return i + 1;
// } catch (Exception e) {
// e.printStackTrace();
// return i + 1;
// }
// }
//
// public List<Product> getProducts(String keycode) {
// try {
// Connection conn = getConnection();
// Statement stat = conn.createStatement();
// ResultSet rs = stat
// .executeQuery("select *  from products where keycode="
// + "'" + keycode + "'");
// List<Product> list = new ArrayList<Product>();
// while (rs.next()) {
// Product product = new Product();
// product.setKeycode(rs.getString("keycode") == null ? "" : rs
// .getString("keycode"));
// product.setDescription(rs.getString("description") == null ? ""
// : rs.getString("description"));
// product.setQuantity(rs.getInt("quantity"));
// product.setPrice(rs.getDouble("price"));
// product.setTax(rs.getDouble("tax"));
// list.add(product);
// }
//
// rs.close();
//
// conn.setAutoCommit(true);
// conn.close();
// return list;
//
// } catch (Exception e) {
// e.printStackTrace();
// return null;
// }
//
// }
// }