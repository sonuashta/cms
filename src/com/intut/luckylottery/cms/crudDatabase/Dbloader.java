package com.intut.luckylottery.cms.crudDatabase;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.intut.luckylottery.cms.domain.Customer;
import com.intut.luckylottery.cms.domain.Fields;
import com.intut.luckylottery.cms.domain.SearchFilter;
import com.intut.luckylottery.cms.domain.SearchResult;
import com.intut.luckylottery.cms.util.Constants;
import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;

public class Dbloader {
	public Dbloader() {
		init();
	}

	private Connection getConnection() throws Exception {
		Connection connection;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:lottery.db");
			return connection;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in creating connection" + e.getMessage());
			throw new Exception("connection error , due to " + e.getMessage());

		}
	}

	private Connection getConnection(String dbName) throws Exception {
		Connection connection;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbName
					+ ".db");
			return connection;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in creating connection" + e.getMessage());
			throw new Exception("connection error , due to " + e.getMessage());

		}
	}

	public void dropTables(String tableName) throws Exception {

		Connection conn = null;
		Statement stat = null;
		try {

			conn = getConnection();

			stat = conn.createStatement();
			stat.executeUpdate("drop table if exists " + tableName + ";");
			conn.close();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in droping tables " + e.getMessage());
			throw new Exception("error in droping tables");

		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public void createBumperTable() throws Exception {

		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			stat = conn.createStatement();
			conn.setAutoCommit(false);
			stat.executeUpdate("create table if not exists bumper ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT collate nocase ,isSelected NUMERIC,"
					+ "createdDate DATETIME,"
					+ "updatedDate DATETIME,"
					+ "deletedDate DATETIME);");
			stat.executeUpdate("create table if not exists messageTemplates ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "message TEXT collate nocase);");
			conn.commit();
		} catch (Exception e) {

			LotteryLogger.getInstance().setError(
					"Error in creating message tables, " + e.getMessage());
			throw new Exception("Exception in creating message table");

		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();

		}

	}

	public void selectBumper(String s) throws Exception {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			String sql = "update  bumper set isSelected =" + 1
					+ " where name='" + s + "'";
			conn = getConnection();
			conn.setAutoCommit(false);
			stat = conn.createStatement();
			stat.execute(sql);
			conn.commit();
		} catch (Exception e) {

			LotteryLogger.getInstance().setError(
					"Error in selecting bumper, " + e.getMessage());
			throw new Exception("Error in selecting bumper");

		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();

		}

	}

	public String getSelectedBumper() throws Exception {
		String bumper = "";

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select name from bumper where isSelected=1");

			while (rs.next()) {

				bumper = rs.getString("name") == null ? "" : rs
						.getString("name");

			}
			return bumper;

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting unique numbers " + e.getMessage());
			throw new Exception("Error in getting unique numbers ");
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public String getDefaultMessage() throws Exception {
		String message = "";

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select message from messageTemplates where id=1");

			while (rs.next()) {

				message = rs.getString("message") == null ? "" : rs
						.getString("message");

			}
			return message;

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting message from template message " + e.getMessage());
			throw new Exception("Error in getting default message");
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}
	
	public void deleteBumper(String bumper) throws Exception {
		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			String sql = "delete from bumper where name='" + bumper + "'";
			conn = getConnection();
			conn.setAutoCommit(false);
			stat = conn.createStatement();
			stat.execute(sql);
			conn.commit();
		} catch (Exception e) {

			LotteryLogger.getInstance().setError(
					"Error in deleting bumper, " + e.getMessage());
			throw new Exception("Error in deleting bumper");

		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();

		}

	}

	public List<String> getBumpers() throws Exception {
		List<String> bumpers = new ArrayList<String>();

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select name from bumper where deletedDate is null ORDER BY name");

			while (rs.next()) {

				String s = rs.getString("name") == null ? "" : rs
						.getString("name");

				bumpers.add(s);
			}

			return bumpers;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting unique numbers " + e.getMessage());
			throw new Exception("Error in getting unique numbers ");
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public void dropTables() throws Exception {

		Connection conn = null;
		Statement stat = null;
		try {

			conn = getConnection();

			stat = conn.createStatement();
			stat.executeUpdate("drop table if exists customer;");
			stat.executeUpdate("drop table if exists processes;");
			conn.close();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in droping tables " + e.getMessage());
			throw new Exception("error in droping tables");

		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public int getSerialNumber() throws Exception {
		int serialNumber = 0;
		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			stat = conn.createStatement();

			ResultSet rs = stat
					.executeQuery("select max(serialNumber) as serialNumber from customer");

			while (rs.next()) {
				serialNumber = rs.getInt("serialNumber");
			}

			rs.close();
			conn.close();
			return ++serialNumber;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting serial Number" + e.getMessage());
			throw new Exception("Error in getting serial Number");
		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}

	}

	public int getSerialNumber(String db) throws Exception {
		int serialNumber = 0;
		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection(db);
			stat = conn.createStatement();

			ResultSet rs = stat
					.executeQuery("select max(serialNumber) as serialNumber from customer");

			while (rs.next()) {
				serialNumber = rs.getInt("serialNumber");
			}

			rs.close();
			conn.close();
			return ++serialNumber;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting serial Number" + e.getMessage());
			throw new Exception("Error in getting serial Number");
		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}

	}

	public boolean init() {
		try {

			Connection conn = getConnection();
			Statement stat = conn.createStatement();

			stat.executeUpdate("create table if not exists customer( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "serialNumber INTEGER,"
					+ "series TEXT,"
					+ "ticketNumber TEXT,"
					+ "name TEXT collate nocase,"
					+ "lotteryType TEXT collate nocase,"
					+ "bumperName TEXT collate nocase,"
					+ "phoneNumber TEXT collate nocase,"
					+ "emailId TEXT collate nocase,"
					+ "address TEXT collate nocase, "
					+ "isMessageSend Numeric,"
					+ "isMailSend Numeric,"
					+ "messageStatus TEXT collate nocase,"
					+ "date DATETIME,"
					+ "createdDate DATETIME,"
					+ "updatedDate DATETIME,"
					+ "deletedDate DATETIME);");
			stat.executeUpdate("create table if not exists processes( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "processName TEXT collate nocase,"
					+ "createdDate DATETIME,"
					+ "updatedDate DATETIME,"
					+ "deletedDate DATETIME);");
			stat.close();
			conn.close();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in creating table customer and prrocesses,"
							+ e.getMessage());
			return false;
		}
		return true;
	}

	public void createMessageTable(String table) throws Exception {

		Connection conn = null;
		Statement stat = null;
		try {
			conn = getConnection();
			stat = conn.createStatement();
			conn.setAutoCommit(false);
			stat.executeUpdate("create table if not exists "
					+ table.toLowerCase()
					+ "Messages ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT collate nocase ,phoneNumber TEXT unique not null,isSend NUMERIC,status TEXT collate nocase,"
					+ "createdDate DATETIME," + "updatedDate DATETIME,"
					+ "deletedDate DATETIME);");
			stat.executeUpdate("create table if not exists "
					+ table.toLowerCase()
					+ "Mails ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "name TEXT collate nocase ,emailId TEXT unique not null,isSend NUMERIC,"
					+ "createdDate DATETIME," + "updatedDate DATETIME,"
					+ "deletedDate DATETIME);");
			insertDummyData(conn, table);
			conn.commit();
		} catch (Exception e) {

			LotteryLogger.getInstance().setError(
					"Error in creating message tables, " + e.getMessage());
			if (conn != null)
				conn.rollback();
			throw new Exception("Exception in creating message table");

		} finally {
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();

		}

	}

	public void insertCustomers(List<Customer> customers) throws Exception {
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			init();
			conn = getConnection();
			conn.setAutoCommit(false);
			for (Customer customer : customers) {

				prep = conn
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

				prep.executeBatch();

				prep.close();

			}
			conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in inserting customers," + e.getMessage());
			if (conn != null)
				conn.rollback();
			throw new Exception("Error in inserting customer," + e.getMessage());

		} finally {
			if (prep != null)
				prep.close();
			if (conn != null)
				conn.close();

		}
	}

	private void insertDummyData(Connection conn, String tableName)
			throws Exception {
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
			LotteryLogger.getInstance().setError(
					"Error in dummy Data, where table name is"
							+ ((Util.isStringNullOrEmpty(tableName) ? ""
									: tableName)) + e.getMessage());
			throw new Exception(
					"Error in inserting dummy data, where table name is"
							+ ((Util.isStringNullOrEmpty(tableName) ? ""
									: tableName)) + e.getMessage());
		}
	}

	public void insertMessageData(String tableName, Customer customer,
			boolean isSend, String status) throws Exception {
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			prep = conn.prepareStatement("insert or ignore into " + tableName
					+ "Messages values(null,?,?,?,?,?,?,?);");

			prep.setString(1, customer.getName());

			prep.setString(2, customer.getPhoneNumber());
			prep.setBoolean(3, isSend);
			prep.setString(4, status);
			prep.setString(5, Util.formatDate(new Date()));
			prep.setString(6, Util.formatDate(new Date()));
			prep.addBatch();

			prep.executeBatch();
			conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"error in saving to database process customer, where table name is"
							+ ((Util.isStringNullOrEmpty(tableName) ? ""
									: tableName)) + e.getMessage());
			if (conn != null)
				conn.rollback();
			throw new Exception(
					"error in saving to database process customer, where table name is"
							+ ((Util.isStringNullOrEmpty(tableName) ? ""
									: tableName)));
		} finally {
			if (prep != null)
				prep.close();
			if (conn != null)
				conn.close();

		}
	}

	public void insertBumper(String bumper) throws Exception {
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			prep = conn.prepareStatement("insert or ignore into bumper"
					+ " values(null,?,?,?,?,?);");

			prep.setString(1, bumper);

			prep.setBoolean(2, false);
			prep.setString(3, Util.formatDate(new Date()));
			prep.setString(4, Util.formatDate(new Date()));
			prep.addBatch();

			prep.executeBatch();
			conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"error in saving bumper ," + e.getMessage());
			if (conn != null)
				conn.rollback();
			throw new Exception("error in saving bumper");
		} finally {
			if (prep != null)
				prep.close();
			if (conn != null)
				conn.close();

		}
	}
	
	public void insertDefaultMessage(String message) throws Exception {
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			prep = conn.prepareStatement("insert or replace into messageTemplates"
					+ " values(1,?);");

			prep.setString(1, message);
			prep.addBatch();

			prep.executeBatch();
			conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"error in saving default message ," + e.getMessage());
			if (conn != null)
				conn.rollback();
			throw new Exception("error in saving default message");
		} finally {
			if (prep != null)
				prep.close();
			if (conn != null)
				conn.close();

		}
	}


	public List<Customer> getUniqueMessagesFromTable(String tableName)
			throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			createMessageTable(tableName);
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select name , phoneNumber  from customer where phoneNumber not in "
							+ "(select phoneNumber from "
							+ tableName
							+ "Messages where "
							+ "isSend=1 or status!='"
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

			return customers;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting unique numbers " + e.getMessage());
			throw new Exception("Error in getting unique numbers ");
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public int getUniqueMessagesCountFromTable(String tableName)
			throws Exception {

		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			createMessageTable(tableName);
			conn = getConnection();

			stat = conn.createStatement();
			rs = stat
					.executeQuery("select count(DISTINCT phoneNumber) as count from customer where phoneNumber not in "
							+ "(select phoneNumber from "
							+ tableName
							+ "Messages where "
							+ "isSend=1 or status !='"
							+ Constants.failedMessaage
							+ "') and phoneNumber!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			return count;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting messages count from table"
							+ (Util.isStringNullOrEmpty(tableName) ? ""
									: tableName) + ", " + e.getMessage());
			throw new Exception("Error in getting messages count from table"
					+ (Util.isStringNullOrEmpty(tableName) ? "" : tableName)
					+ ", " + e.getMessage());

		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public int getUniqueMailsCountFromTable(String tableName) throws Exception {

		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			createMessageTable(tableName);
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select count(DISTINCT emailId) as count from customer where emailId not in "
							+ "(select emailId from "
							+ tableName
							+ "Mails where " + "isSend=0) and emailId!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			return count;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting mails count from table"
							+ (Util.isStringNullOrEmpty(tableName) ? ""
									: tableName) + ", " + e.getMessage());
			throw new Exception("Error in getting mails count from table"
					+ (Util.isStringNullOrEmpty(tableName) ? "" : tableName)
					+ ", " + e.getMessage());

		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public int getUniqueCustomersMessagesCount() throws Exception {

		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select count(DISTINCT phoneNumber) as count from customer where phoneNumber!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			return count;
		} catch (Exception e) {

			LotteryLogger.getInstance().setError(
					"Error in getting message customer count ,"
							+ e.getMessage());
			throw new Exception("Error in getting message customer count ,"
					+ e.getMessage());

		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public int getUniqueCustomersMailsCount() throws Exception {

		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select count(DISTINCT emailId) as count from customer where emailId!=''");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			return count;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting mails count ," + e.getMessage());
			throw new Exception("Error in getting mails count ,"
					+ e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public int getCustomersCount() throws Exception {

		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery("select count(id) as count from customer");

			while (rs.next()) {
				count = rs.getInt("count");

			}

			return count;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting customers count ," + e.getMessage());
			throw new Exception("Error in getting customers count");
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public List<Customer> getUniqueMailsFromTable(String tableName)
			throws Exception {
		List<Customer> customers = new ArrayList<Customer>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			createMessageTable(tableName);
			conn = getConnection();
			stat = conn.createStatement();
			rs = stat
					.executeQuery("select name,emailId  from customer where phoneNumber not in "
							+ "(select emailId from "
							+ tableName
							+ "Mails where "
							+ "isSend=0 )  and emailId!=''  group by emailId ORDER BY id LIMIT 0, 1000");

			while (rs.next()) {
				Customer customer = new Customer();

				customer.setName(rs.getString(Fields.name) == null ? "" : rs
						.getString(Fields.name));
				customer.setEmailId(rs.getString(Fields.emailId) == null ? ""
						: rs.getString(Fields.emailId));
				customers.add(customer);
			}

			return customers;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting unique mails count ," + e.getMessage());
			throw new Exception("Error in getting unique mails count ,"
					+ e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public boolean insertProcess(String processName) throws Exception {
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		PreparedStatement prep = null;
		;
		try {
			init();
			conn = getConnection();

			try {
				stat = conn.createStatement();
				rs = stat
						.executeQuery("select id  from processes where processName='"
								+ processName + "'");

				while (rs.next()) {
					return false;

				}
			} catch (Exception e) {
				LotteryLogger.getInstance().setError(
						"Error in checking process " + e.getMessage());
				throw new Exception("Error in checking process");

			} finally {
				if (rs != null)
					rs.close();
				if (stat != null)
					stat.close();
				if (conn != null)
					conn.close();
			}
			conn = getConnection();
			conn.setAutoCommit(false);
			prep = conn.prepareStatement("insert into processes values ( null,"
					+ "?,?, ?,?);");

			prep.setString(1, processName);

			prep.setString(2, Util.formatDate(new Date()));
			prep.setString(3, Util.formatDate(new Date()));
			prep.addBatch();

			prep.executeBatch();
			conn.commit();
			return true;
		}

		catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in inserting process" + e.getMessage());

			if (conn != null)
				conn.rollback();
			throw new Exception("Database Error");
		} finally {
			if (prep != null)
				prep.close();
			if (conn != null)
				conn.close();

		}
	}

	public List<String> getProcesses() throws Exception {
		List<String> processes = new ArrayList<String>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		PreparedStatement prep = null;

		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery("select * from processes");

			while (rs.next()) {
				processes.add(rs.getString("processName"));
			}

		} catch (Exception e) {
			LotteryLogger.getInstance().setError("Error in getting processes");
			throw new Exception("Error in getting processes");

		} finally {
			if (rs != null)
				rs.close();
			if (prep != null)
				prep.close();
			if (conn != null)
				conn.close();

		}
		return processes;
	}

	public void deleteProcess(String processName) throws Exception {
		Connection conn = null;
		PreparedStatement prest = null;

		try {
			conn = getConnection();
			String sql = "UPDATE processes SET deletedDate = ? ,updatedDate = ? WHERE processName='"
					+ processName + "'";
			prest = conn.prepareStatement(sql);
			prest.setString(0, Util.formatDate(new Date()));

			prest.setString(1, Util.formatDate(new Date()));

			prest.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in updating process delete date" + e.getMessage());
			throw new Exception("Error in updating process date");
		} finally {

			if (prest != null)
				prest.close();
			if (conn != null)
				conn.close();

		}
	}

	public void updateMessageOfTable(String table, boolean isSend,
			String status, String phoneNumber) throws Exception {
		Connection conn = null;
		PreparedStatement prest = null;
		try {
			conn = getConnection();
			String sql = "UPDATE "
					+ table
					+ "Messages SET isSend = ? , status = ? , updatedDate = ? WHERE "
					+ Fields.phoneNumber + " = ?";
			prest = conn.prepareStatement(sql);
			prest.setBoolean(0, isSend);
			prest.setString(1, status);
			prest.setString(2, Util.formatDate(new Date()));
			prest.setString(3, phoneNumber);
			conn.setAutoCommit(false);
			prest.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in updating message table where table is" + table
							+ e.getMessage());
			throw new Exception("Error in updating process date");
		} finally {

			if (prest != null)
				prest.close();
			if (conn != null)
				conn.close();

		}
	}

	public void updateMailsOfTable(String table, boolean isSend, String status,
			String emailId) throws Exception {
		Connection conn = null;
		PreparedStatement prest = null;
		try {
			conn = getConnection();
			String sql = "UPDATE "
					+ table
					+ "Messages SET isSend = ?  , status = ? ,updatedDate = ? WHERE "
					+ Fields.emailId + " = ?";
			prest = conn.prepareStatement(sql);
			prest.setBoolean(0, isSend);
			prest.setString(1, status);
			prest.setString(2, Util.formatDate(new Date()));
			prest.setString(3, emailId);
			prest.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in updating mails table where table is" + table
							+ e.getMessage());
			throw new Exception("Error in updating emails");
		} finally {

			if (prest != null)
				prest.close();
			if (conn != null)
				conn.close();

		}
	}

	public void updateCustomerMessage(String table, boolean isSend,
			String status, String emailId) throws Exception {
		Connection conn = null;
		PreparedStatement prest = null;
		try {
			conn = getConnection();
			String sql = "UPDATE "
					+ table
					+ "Messages SET isSend = ?  , status = ? ,updatedDate = ? WHERE "
					+ Fields.emailId + " = ?";
			prest = conn.prepareStatement(sql);
			prest.setBoolean(0, isSend);
			prest.setString(1, status);
			prest.setString(2, Util.formatDate(new Date()));
			prest.setString(3, emailId);
			prest.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in updating customer message where table is" + table
							+ e.getMessage());
			throw new Exception("Error in updating emails");
		} finally {

			if (prest != null)
				prest.close();
			if (conn != null)
				conn.close();

		}
	}

	public void insertbackupLotteries(List<Customer> customers)
			throws Exception {

		Connection conn = null;

		try {
			init();
			conn = getConnection();
			conn.setAutoCommit(false);
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
				prep.executeBatch();
				prep.close();
			}
			conn.commit();
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in inserting backup data" + e.getMessage());
			if (conn != null)
				conn.rollback();
			throw new Exception("Error in inserting backup data");

		} finally {
			if (conn != null)
				conn.close();

		}
	}

	public void updateCustomer(Customer customer, boolean isMessageSend,
			boolean isMailSend, String status) throws Exception {

		Connection conn = null;
		PreparedStatement prep = null;
		try {

			init();
			String sql = "delete from customer where serialNumber="
					+ customer.getSerialNumber();
			conn = getConnection();
			conn.setAutoCommit(false);
			Statement stat = conn.createStatement();
			stat.execute(sql);
			prep = conn.prepareStatement("insert into customer values ( null,"
					+ "?," + "?," + "?," + " ?," + "?," + " ?," + "?," + "? ,"
					+ "? ," + "?," + "?," + "?," + "? , ?, ?,?);");

			prep.setInt(1, customer.getSerialNumber());
			prep.setString(2, "");
			prep.setString(3, customer.getSeries().trim()
					+ customer.getTicketNumber().trim());
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

			prep.executeBatch();
			conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in updating customer" + e.getMessage());

			throw new Exception("Error in inserting updating customers");

		} finally {
			if (prep != null)
				prep.close();
			if (conn != null)
				conn.close();

		}

	}

	public void savePendings(List<Customer> customers, boolean isMessageSend,
			boolean isMailSend, String status) throws Exception {
		Connection conn = null;
		PreparedStatement prest = null;

		try {
			init();

			conn = getConnection();
			conn.setAutoCommit(false);

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
				prep.setBoolean(10, isMessageSend);
				prep.setBoolean(11, isMailSend);
				prep.setString(12, status);
				prep.setString(13, Util.formatDate(customer.getDate()));
				prep.setString(14, Util.formatDate(new Date()));
				prep.setString(15, Util.formatDate(new Date()));
				prep.addBatch();

				prep.executeBatch();

				prep.close();

			}
			conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in inserting pending customers" + e.getMessage());
			if (conn != null)
				conn.rollback();
			throw new Exception("Error in inserting pending customers");

		} finally {
			if (conn != null)
				conn.close();

		}
	}

	public boolean dropDatabase() throws Exception {
		File file = new File("lottery.db");
		boolean result = file.delete();
		return result;

	}

	public int searchResultCount(SearchFilter searchFilter) throws Exception {

		String query = "select count(id) as count from customer where id>=1 ";
		if (!Util.isStringNullOrEmpty(searchFilter.getName()))
			query += "and name= '" + searchFilter.getName() + "' ";
		if (searchFilter.getFromdate() != null)
			query += "and date >= '"
					+ Util.formatDate(Util.getStartingdate(searchFilter
							.getFromdate())) + "' and date < '"
					+ Util.getNextDate(searchFilter.getToDate()) + "'";
		if (!Util.isStringNullOrEmpty(searchFilter.getBumperName())
				&& !Util.isStringNullOrEmpty(searchFilter.getBumperName())) {
			if (searchFilter.isBothSelected())
				query += " and (lotteryType='monthly' or bumperName='"
						+ searchFilter.getBumperName() + "' )";
			else {
				query += " and bumperName='" + searchFilter.getBumperName()
						+ "' ";
			}
		} else if (!Util.isStringNullOrEmpty(searchFilter.getBumperName())) {
			query += " and bumperName='" + searchFilter.getBumperName() + "' ";
		} else {
			query += " and lotteryType='monthly'";
		}

		if (!Util.isStringNullOrEmpty(searchFilter.getPhoneNumber())) {
			query += " and phoneNumber='" + searchFilter.getPhoneNumber()
					+ "' ";
		}
		if (!Util.isStringNullOrEmpty(searchFilter.getTicketNumber())) {
			query += " and ticketNumber='" + searchFilter.getTicketNumber()
					+ "' ";
		}
		if (!Util.isStringNullOrEmpty(searchFilter.getEmailId())) {
			query += " and emailId='" + searchFilter.getEmailId() + "' ";
		}
		if (!Util.isStringNullOrEmpty(searchFilter.getAddress())) {
			query += " and address='" + searchFilter.getAddress() + "' ";
		}
		if (searchFilter.isMessageSend()) {
			query += "and (isMessageSend=1)";
		}
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(query);
			int count = 0;
			while (rs.next()) {
				count = rs.getInt("count");
			}

			return count;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting filtered customers ," + e.getMessage());
			throw new Exception("Error in getting filtered customers,");
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public SearchResult getCustomers(SearchFilter searchFilter)
			throws Exception {
		SearchResult searchResult = new SearchResult();
		List<Customer> customers = new ArrayList<Customer>();
		String query = "select * from customer where id>=1 ";
		if (!Util.isStringNullOrEmpty(searchFilter.getName()))
			query += "and name= '" + searchFilter.getName() + "' ";
		if (searchFilter.getFromdate() != null)
			query += "and date >= '"
					+ Util.formatDate(Util.getStartingdate(searchFilter
							.getFromdate())) + "' and date < '"
					+ Util.getNextDate(searchFilter.getToDate()) + "'";
		if (!Util.isStringNullOrEmpty(searchFilter.getBumperName())
				&& !Util.isStringNullOrEmpty(searchFilter.getBumperName())) {
			if (searchFilter.isBothSelected())
				query += " and (lotteryType='monthly' or bumperName='"
						+ searchFilter.getBumperName() + "' )";
			else {
				query += " and bumperName='" + searchFilter.getBumperName()
						+ "' ";
			}
		} else if (!Util.isStringNullOrEmpty(searchFilter.getBumperName())) {
			query += " and bumperName='" + searchFilter.getBumperName() + "' ";
		} else {
			query += " and lotteryType='monthly'";
		}

		if (!Util.isStringNullOrEmpty(searchFilter.getPhoneNumber())) {
			query += " and phoneNumber='" + searchFilter.getPhoneNumber()
					+ "' ";
		}
		if (!Util.isStringNullOrEmpty(searchFilter.getTicketNumber())) {
			query += " and ticketNumber='" + searchFilter.getTicketNumber()
					+ "' ";
		}
		if (!Util.isStringNullOrEmpty(searchFilter.getEmailId())) {
			query += " and emailId='" + searchFilter.getEmailId() + "' ";
		}
		if (!Util.isStringNullOrEmpty(searchFilter.getAddress())) {
			query += " and address='" + searchFilter.getAddress() + "' ";
		}
		if (searchFilter.isMessageSend()) {
			query += "and (isMessageSend=1 )";
		}
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {

			conn = getConnection();
			stat = conn.createStatement();
			rs = stat.executeQuery(query + " ORDER BY id LIMIT "
					+ searchFilter.getPageCount()
					* (searchFilter.getPageNumber() - 1) + ", "
					+ (searchFilter.getPageCount()));

			while (rs.next()) {
				Customer customer = new Customer();

				customer.setName(rs.getString(Fields.name) == null ? "" : rs
						.getString(Fields.name));
				customer.setEmailId(rs.getString(Fields.emailId) == null ? ""
						: rs.getString(Fields.emailId));
				customer.setAddress(rs.getString(Fields.address) == null ? ""
						: rs.getString(Fields.address));
				customer.setBumperName(rs.getString(Fields.bumperName) == null ? ""
						: rs.getString(Fields.bumperName));
				customer.setDate(rs.getString(Fields.date) == null ? Util
						.getDate("") : Util.getDate(rs.getString(Fields.date)));
				customer.setLotteryType(rs.getString(Fields.lotteryType) == null ? ""
						: rs.getString(Fields.lotteryType));
				customer.setPhoneNumber(rs.getString(Fields.phoneNumber) == null ? ""
						: rs.getString(Fields.phoneNumber));
				customer.setSerialNumber(rs.getInt(Fields.serialNumber));
				customer.setTicketNumber(rs.getString(Fields.ticketNumber) == null ? ""
						: rs.getString(Fields.ticketNumber));
				customers.add(customer);
			}
			searchResult.totalResults = searchResultCount(searchFilter);
			searchResult.customers = customers;
			searchResult.searchFilter = searchFilter;
			return searchResult;
		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in getting filtered customers ," + e.getMessage());
			throw new Exception("Error in getting filtered customers,");
		} finally {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (conn != null)
				conn.close();
		}
	}

	public void mergeDatabase(String mainDb, String toBeMergeDb)
			throws Exception {

		int serialNumber = getSerialNumber(mainDb);
		Connection db1Conn = getConnection(mainDb);
		Connection db2Conn = getConnection(toBeMergeDb);
		Statement stat = db2Conn.createStatement();
		String query = "select * from customer";
		ResultSet rs = stat.executeQuery(query);
		try {
			while (rs.next()) {
				PreparedStatement prep = db1Conn
						.prepareStatement("insert into customer values ( null,"
								+ "?," + "?," + "?," + " ?," + "?," + " ?,"
								+ "?," + "? ," + "? ," + "?," + "?," + "?,"
								+ "? , ?, ?,?);");

				prep.setInt(1, serialNumber++);
				prep.setString(
						2,
						rs.getString(Fields.series) == null ? "" : rs
								.getString(Fields.series));
				prep.setString(
						3,
						rs.getString(Fields.ticketNumber) == null ? "" : rs
								.getString(Fields.ticketNumber));
				prep.setString(
						4,
						rs.getString(Fields.name) == null ? "" : rs
								.getString(Fields.name));
				prep.setString(5, rs.getString(Fields.lotteryType) == null ? ""
						: rs.getString(Fields.lotteryType));
				prep.setString(6, rs.getString(Fields.bumperName) == null ? ""
						: rs.getString(Fields.bumperName));
				prep.setString(7, rs.getString(Fields.phoneNumber) == null ? ""
						: rs.getString(Fields.phoneNumber));
				prep.setString(8, rs.getString(Fields.emailId) == null ? ""
						: rs.getString(Fields.emailId));
				prep.setString(9, rs.getString(Fields.address) == null ? ""
						: rs.getString(Fields.address));
				prep.setBoolean(10, rs.getBoolean("isMessageSend"));
				prep.setBoolean(11, rs.getBoolean("isMailSend"));
				prep.setString(12, rs.getString("messageStatus") == null ? ""
						: rs.getString("messageStatus"));
				prep.setString(
						13,
						rs.getString("date") == null ? "" : rs
								.getString("date"));
				prep.setString(14, rs.getString("createdDate") == null ? ""
						: rs.getString("createdDate"));
				prep.setString(15, rs.getString("updatedDate") == null ? ""
						: rs.getString("updatedDate"));
				prep.setString(15, rs.getString("deletedDate") == null ? ""
						: rs.getString("deletedDate"));
				prep.addBatch();
				prep.executeBatch();
				prep.executeBatch();

				prep.close();

			}
			rs.close();
			db1Conn.commit();

		} catch (Exception e) {
			LotteryLogger.getInstance().setError(
					"Error in inserting pending customers" + e.getMessage());
			if (db1Conn != null)
				db1Conn.rollback();
			throw new Exception("Error in inserting pending customers");

		} finally {
			if (db2Conn != null)
				db2Conn.close();
			if (db1Conn != null)
				db1Conn.close();

		}

	}

	// public void sendMessages(SearchFilter searchFilter, IProgressMonitor
	// monitor) {
	// // TODO Auto-generated method stub
	//
	// }
}
