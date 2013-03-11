package com.intut.luckylottery.crudDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.intut.luckylottery.cms.util.Constants;
import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;
import com.intut.luckylottery.domain.Customer;
import com.intut.luckylottery.domain.Fields;

public class Dbloader {

	private Connection getConnection() {
		Connection connection;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:lottery.db");
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
			stat.executeUpdate("drop table if exists customer;");
			stat.executeUpdate("drop table if exists processes;");
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public int getSerialNumber() {
		int serialNumber = 0;
		try {
			Connection conn = getConnection();
			Statement stat = conn.createStatement();

			ResultSet rs = stat
					.executeQuery("select max(serialNumber) as serialNumber from customer");

			while (rs.next()) {
				serialNumber = rs.getInt("serialNumber");
			}

			rs.close();
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return ++serialNumber;
	}

	public boolean init() {
		try {

			Connection conn = getConnection();
			Statement stat = conn.createStatement();

			stat.executeUpdate("create table if not exists customer( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "serialNumber INTEGER,"
					+ "series TEXT,"
					+ "ticketNumber TEXT,"
					+ "name TEXT,"
					+ "lotteryType TEXT,"
					+ "bumperName TEXT,"
					+ "phoneNumber TEXT,"
					+ "emailId TEXT,"
					+ "address TEXT, "
					+ "isMessageSend Numeric,"
					+ "isMailSend Numeric,"
					+ "messageStatus TEXT,"
					+ "date date,"
					+ "createdDate date,"
					+ "updatedDate date,"
					+ "deletedDate date);");
			stat.executeUpdate("create table if not exists processes( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "processName TEXT,"
					+ "createdDate date,"
					+ "updatedDate date," + "deletedDate date);");
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void createMessageTable(String table) {
		try {

			Connection conn = getConnection();
			Statement stat = conn.createStatement();

			stat.executeUpdate("create table if not exists "
					+ table.toLowerCase()
					+ "Messages ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,phoneNumber TEXT unique not null,isSend NUMERIC,status TEXT,"
					+ "createdDate date," + "updatedDate date,"
					+ "deletedDate date);");
			stat.executeUpdate("create table if not exists "
					+ table.toLowerCase()
					+ "Mails ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT,emailId TEXT unique not null,isSend NUMERIC,"
					+ "createdDate date," + "updatedDate date,"
					+ "deletedDate date);");
			insertDummyData(conn, table);
			stat.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertCustomers(List<Customer> customers) {
		try {
			init();
			Connection conn = getConnection();

			for (Customer customer : customers) {

				PreparedStatement prep = conn
						.prepareStatement("insert into customer values ( null,"
								+ "?," + "?," + "?," + " ?," + "?," + " ?,"
								+ "?," + "? ," + "? ," + "?," + "?," + "?,"
								+ "? , ?, ?);");

				prep.setInt(1, customer.getSerialNumber());
				prep.setString(2, customer.getSeries());
				prep.setString(3, customer.getTicketNumber());
				prep.setString(4, customer.getName());
				prep.setString(5, customer.getLotteryType());
				prep.setString(6, customer.getBumperName());
				prep.setString(7, customer.getPhoneNumber());
				prep.setString(8, customer.getEmailId());
				prep.setString(9, customer.getAddress());
				prep.setBoolean(10, false);
				prep.setBoolean(11, false);
				prep.setString(12, Constants.backupMessage);
				prep.setString(13, Util.formatDate(customer.getDate()));
				prep.setString(14, Util.formatDate(new Date()));
				prep.setString(15, Util.formatDate(new Date()));
				prep.addBatch();
				conn.setAutoCommit(false);
				prep.executeBatch();
				conn.setAutoCommit(true);
				prep.close();

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void insertDummyData(Connection conn, String tableName) {
		try {
			Statement stat = conn.createStatement();

			stat.executeUpdate("insert or ignore into "
					+ tableName
					+ "Messages (name, phoneNumber) values('dummy name','1234567890');");
			stat.executeUpdate("insert or ignore into "
					+ tableName
					+ "Mails (name, emailId) values('dummy name','dummy@dummy.com');");

			stat.close();
		} catch (Exception e) {

		}
	}

	public List<Customer> getUniqueMessagesFromTable(String tableName) {
		List<Customer> customers = new ArrayList<Customer>();

		try {
			createMessageTable(tableName);
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat
					.executeQuery("select name , phoneNumber  from customer where phoneNumber not in "
							+ "(select phoneNumber from "
							+ tableName
							+ "Messages where "
							+ "isSend=0 or status='"
							+ Constants.failedMessaage
							+ "') and phoneNumber!='' group by phoneNumber ORDER BY id LIMIT 0, 1000");

			while (rs.next()) {
				Customer customer = new Customer();

				customer.setName(rs.getString(Fields.name) == null ? "" : rs
						.getString(Fields.name));
				customer.setPhoneNumber(rs.getString(Fields.phoneNumber) == null ? ""
						: rs.getString(Fields.phoneNumber));
				customers.add(customer);
			}

			rs.close();

			conn.setAutoCommit(true);
			conn.close();
			return customers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getUniqueMessagesCountFromTable(String tableName) {

		int count = 0;
		try {
			createMessageTable(tableName);
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat
					.executeQuery("select count(DISTINCT phoneNumber) as count from customer where phoneNumber not in "
							+ "(select phoneNumber from "
							+ tableName
							+ "Messages where "
							+ "isSend=0 or status='"
							+ Constants.failedMessaage
							+ "') and phoneNumber!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			rs.close();
			stat.close();
			conn.setAutoCommit(true);
			conn.close();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int getUniqueMailsCountFromTable(String tableName) {

		int count = 0;
		try {
			createMessageTable(tableName);
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat
					.executeQuery("select count(DISTINCT emailId) as count from customer where emailId not in "
							+ "(select emailId from "
							+ tableName
							+ "Mails where " + "isSend=0) and emailId!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			rs.close();
			stat.close();
			conn.setAutoCommit(true);
			conn.close();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int getUniqueCustomersMessagesCount() {

		int count = 0;
		try {

			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat
					.executeQuery("select count(DISTINCT phoneNumber) as count from customer where phoneNumber!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			rs.close();
			stat.close();
			conn.setAutoCommit(true);
			conn.close();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int getUniqueCustomersMailsCount() {

		int count = 0;
		try {

			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat
					.executeQuery("select count(DISTINCT emailId) as count from customer where emailId!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			rs.close();
			stat.close();
			conn.setAutoCommit(true);
			conn.close();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<Customer> getUniqueMailsFromTable(String tableName) {
		List<Customer> customers = new ArrayList<Customer>();

		try {
			createMessageTable(tableName);
			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat
					.executeQuery("select name,emailId  from customer where phoneNumber not in "
							+ "(select emailId from "
							+ tableName
							+ "Mails where "
							+ "isSend=0 )  and emailId!=''  group by emailId ORDER BY id LIMIT 0, 1000");

			while (rs.next()) {
				Customer customer = new Customer();

				customer.setName(rs.getString(Fields.name) == null ? "" : rs
						.getString(Fields.name));
				customer.setEmailId(rs.getString(Fields.emailId) == null ? "" : rs
						.getString(Fields.emailId));
				customers.add(customer);
			}

			rs.close();

			conn.setAutoCommit(true);
			conn.close();
			return customers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean insertProcess(String processName) throws Exception {
		try {
			init();
			Connection conn = getConnection();

			try {
				Statement stat = conn.createStatement();
				ResultSet rs = stat
						.executeQuery("select id  from processes where processName='"
								+ processName + "'");

				while (rs.next()) {
					rs.close();
					stat.close();
					conn.close();
					return false;

				}
			} catch (Exception e) {
				LotteryLogger.getInstance().setError(
						"Error in checking process " + e.getMessage());
			}
			PreparedStatement prep = conn
					.prepareStatement("insert into processes values ( null,"
							+ "?,?, ?,?);");

			prep.setString(1, processName);

			prep.setString(2, Util.formatDate(new Date()));
			prep.setString(3, Util.formatDate(new Date()));
			prep.addBatch();
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
			prep.close();
			conn.close();

		}

		catch (Exception e) {
			LotteryLogger.getInstance().setError("Error in inserting process");
			throw new Exception(e);

		}
		return true;
	}

	public List<String> getProcesses() {
		List<String> processes = new ArrayList<String>();
		try {

			Connection conn = getConnection();
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from processes");

			while (rs.next()) {
				processes.add(rs.getString("processName"));
			}

			rs.close();
			stat.close();
			conn.setAutoCommit(true);
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return processes;
	}

	public void deleteProcess(String processName) {
		try {
			Connection conn = getConnection();
			String sql = "UPDATE processes SET deletedDate = ? ,updatedDate = ? WHERE processName='"
					+ processName + "'";
			PreparedStatement prest = conn.prepareStatement(sql);
			prest.setString(0, Util.formatDate(new Date()));

			prest.setString(1, Util.formatDate(new Date()));

			prest.executeUpdate();
			conn.close();
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in updating process delete date" + e.getMessage());
		}
	}

	public void updateMessageOfTable(String table, boolean isSend,
			String status, String phoneNumber) {
		try {
			Connection conn = getConnection();
			String sql = "UPDATE "
					+ table
					+ "Messages SET isSend = ? , status = ? , updatedDate = ? WHERE "
					+ Fields.phoneNumber + " = ?";
			PreparedStatement prest = conn.prepareStatement(sql);
			prest.setBoolean(0, isSend);
			prest.setString(1, status);
			prest.setString(2, Util.formatDate(new Date()));
			prest.setString(3, phoneNumber);
			prest.executeUpdate();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateMailsOfTable(String table, boolean isSend, String status,
			String emailId) {
		try {
			Connection conn = getConnection();
			String sql = "UPDATE "
					+ table
					+ "Messages SET isSend = ?  , status = ? ,updatedDate = ? WHERE "
					+ Fields.emailId + " = ?";
			PreparedStatement prest = conn.prepareStatement(sql);
			prest.setBoolean(0, isSend);
			prest.setString(1, status);
			prest.setString(2, Util.formatDate(new Date()));
			prest.setString(3, emailId);
			prest.executeUpdate();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateCustomerMessage(String table, boolean isSend,
			String status, String emailId) {
		try {
			Connection conn = getConnection();
			String sql = "UPDATE "
					+ table
					+ "Messages SET isSend = ?  , status = ? ,updatedDate = ? WHERE "
					+ Fields.emailId + " = ?";
			PreparedStatement prest = conn.prepareStatement(sql);
			prest.setBoolean(0, isSend);
			prest.setString(1, status);
			prest.setString(2, Util.formatDate(new Date()));
			prest.setString(3, emailId);
			prest.executeUpdate();
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void insertbackupLotteries(List<Customer> customers) {
		try {
			init();
			Connection conn = getConnection();

			for (Customer customer : customers) {

				PreparedStatement prep = conn
						.prepareStatement("insert into customer values ( null,"
								+ "?," + "?," + "?," + " ?," + "?," + " ?,"
								+ "?," + "? ," + "? ," + "?," + "?," + "?,"
								+ "? , ?, ?,?);");

				prep.setInt(1, customer.getSerialNumber());
				prep.setString(2, customer.getSeries());
				prep.setString(3, customer.getTicketNumber());
				prep.setString(4, customer.getName());
				prep.setString(5, customer.getLotteryType());
				prep.setString(6, customer.getBumperName());
				prep.setString(7, customer.getPhoneNumber());
				prep.setString(8, customer.getEmailId());
				prep.setString(9, customer.getAddress());
				prep.setBoolean(10, false);
				prep.setBoolean(11, false);
				prep.setString(12, Constants.backupMessage);
				prep.setString(13, Util.formatDate(customer.getDate()));
				prep.setString(14, Util.formatDate(new Date()));
				prep.setString(15, Util.formatDate(new Date()));
				prep.addBatch();
				conn.setAutoCommit(false);
				prep.executeBatch();
				conn.setAutoCommit(true);
				prep.close();

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void updateCustomer(Customer customer, boolean isMessageSend,
			boolean isMailSend, String status) {
		try {
			init();
			String sql = "delete from customer where serialNumber="
					+ customer.getSerialNumber();
			Connection conn = getConnection();

			Statement stat = conn.createStatement();
			stat.execute(sql);
			PreparedStatement prep = conn
					.prepareStatement("insert into customer values ( null,"
							+ "?," + "?," + "?," + " ?," + "?," + " ?," + "?,"
							+ "? ," + "? ," + "?," + "?," + "?,"
							+ "? , ?, ?,?);");

			prep.setInt(1, customer.getSerialNumber());
			prep.setString(2, customer.getSeries());
			prep.setString(3, customer.getTicketNumber());
			prep.setString(4, customer.getName());
			prep.setString(5, customer.getLotteryType());
			prep.setString(6, customer.getBumperName());
			prep.setString(7, customer.getPhoneNumber());
			prep.setString(8, customer.getEmailId());
			prep.setString(9, customer.getAddress());
			prep.setBoolean(10, isMessageSend);
			prep.setBoolean(11, isMailSend);
			prep.setString(12, status);
			prep.setString(13, Util.formatDate(customer.getDate()));
			prep.setString(14, Util.formatDate(new Date()));
			prep.setString(15, Util.formatDate(new Date()));
			prep.addBatch();
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
			prep.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savePendings(List<Customer> customers, boolean isMessageSend,
			boolean isMailSend, String status) {
		try {
			init();

			Connection conn = getConnection();

			for (Customer customer : customers) {
				String sql = "delete from customer where serialNumber="
						+ customer.getSerialNumber();

				Statement stat = conn.createStatement();
				stat.execute(sql);
				PreparedStatement prep = conn
						.prepareStatement("insert  into customer values ( null,"
								+ "?,"
								+ "?,"
								+ "?,"
								+ " ?,"
								+ "?,"
								+ " ?,"
								+ "?,"
								+ "? ,"
								+ "? ,"
								+ "?,"
								+ "?,"
								+ "?,"
								+ "? , ?, ?,?) ;");

				prep.setInt(1, customer.getSerialNumber());
				prep.setString(2, customer.getSeries());
				prep.setString(3, customer.getTicketNumber());
				prep.setString(4, customer.getName());
				prep.setString(5, customer.getLotteryType());
				prep.setString(6, customer.getBumperName());
				prep.setString(7, customer.getPhoneNumber());
				prep.setString(8, customer.getEmailId());
				prep.setString(9, customer.getAddress());
				prep.setBoolean(10, isMailSend);
				prep.setBoolean(11, isMailSend);
				prep.setString(12, status);
				prep.setString(13, Util.formatDate(customer.getDate()));
				prep.setString(14, Util.formatDate(new Date()));
				prep.setString(15, Util.formatDate(new Date()));
				prep.addBatch();
				conn.setAutoCommit(false);
				prep.executeBatch();
				conn.setAutoCommit(true);
				prep.close();

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
