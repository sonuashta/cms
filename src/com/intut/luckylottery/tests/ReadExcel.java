package com.intut.luckylottery.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.intut.luckylottery.cms.util.LotteryLogger;
import com.intut.luckylottery.cms.util.Util;
import com.intut.luckylottery.domain.Customer;

public class ReadExcel {

	public List<Customer> read(File file, String lotteryType, String bumperName)
			throws IOException {
		List<Customer> customers = new ArrayList<Customer>();
		try {

			FileInputStream fis = null;
			fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			boolean isFirst = true;
			for (Row row : sheet) {
				if (isFirst) {
					isFirst = false;
					continue;
				}
				Customer customer = new Customer();
				if (!Util.isStringNullOrEmpty(bumperName))
					customer.setBumperName(bumperName);
				customer.setLotteryType(lotteryType);
				if (readColumns(row, customer) != null)
					customers.add(customer);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LotteryLogger.getInstance().setError(
					"Error while reading File " + e.getMessage());
		}

		return customers;
	}

	private Customer readColumns(Row row, Customer customer) {
		try {
			for (Cell cell : row) {
				if (cell.getColumnIndex() == 0
						&& Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					customer.setSerialNumber((int) cell.getNumericCellValue());
					continue;
				} else if (cell.getColumnIndex() == 1
						&& Cell.CELL_TYPE_NUMERIC == cell.getCellType()
						&& DateUtil.isCellDateFormatted(cell)) {
					customer.setDate(cell.getDateCellValue());
					continue;
				} else if (cell.getColumnIndex() == 2
						&& Cell.CELL_TYPE_STRING == cell.getCellType()) {
					customer.setSeries(cell.getRichStringCellValue()
							.getString());
					continue;
				} else if (cell.getColumnIndex() == 3
						&& (Cell.CELL_TYPE_STRING == cell.getCellType() || Cell.CELL_TYPE_NUMERIC == cell
								.getCellType())) {
					if (Cell.CELL_TYPE_STRING == cell.getCellType())
						customer.setTicketNumber(cell.getRichStringCellValue()
								.getString());
					else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType())
						customer.setTicketNumber(""
								+ (long) cell.getNumericCellValue());
					continue;
				} else if (cell.getColumnIndex() == 4
						&& Cell.CELL_TYPE_STRING == cell.getCellType()) {
					customer.setName(cell.getRichStringCellValue().getString());
					continue;
				} else if (cell.getColumnIndex() == 5
						&& (Cell.CELL_TYPE_STRING == cell.getCellType() || Cell.CELL_TYPE_NUMERIC == cell
								.getCellType())) {
					if (Cell.CELL_TYPE_STRING == cell.getCellType())
						customer.setPhoneNumber(cell.getRichStringCellValue()
								.getString());
					else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType())
						customer.setPhoneNumber(""
								+ (long) cell.getNumericCellValue());
					continue;
				} else if (cell.getColumnIndex() == 6
						&& Cell.CELL_TYPE_STRING == cell.getCellType()) {
					customer.setEmailId(cell.getRichStringCellValue()
							.getString());
					continue;
				} else if (cell.getColumnIndex() == 7
						&& Cell.CELL_TYPE_STRING == cell.getCellType()) {
					customer.setAddress(cell.getRichStringCellValue()
							.getString());
					continue;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			LotteryLogger.getInstance().setError(
					"Error in reading row of excel, where exception is"
							+ e.getMessage());
			return null;
		}
		return customer;
	}

}