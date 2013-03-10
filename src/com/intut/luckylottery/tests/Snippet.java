package com.intut.luckylottery.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

public class Snippet {
	public static void main(String a[]){
	
	
	
	try {
	     
	    FileInputStream file = new FileInputStream(new File("C:\\Users\\KESHAV\\Desktop\\cms\\test.xls"));
	     
	    //Get the workbook instance for XLS file 
	    HSSFWorkbook workbook = new HSSFWorkbook(file);
	 
	    //Get first sheet from the workbook
	    HSSFSheet sheet = workbook.getSheetAt(0);
	     
	    //Iterate through each rows from first sheet
	    Iterator<Row> rowIterator = sheet.iterator();
	    while(rowIterator.hasNext()) {
	        Row row = rowIterator.next();
	         
	        //For each row, iterate through each columns
	        Iterator<Cell> cellIterator = row.cellIterator();
	        while(cellIterator.hasNext()) {
	             
	            Cell cell = cellIterator.next();
	             
	            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    System.out.println(cell.getRichStringCellValue().getString());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        System.out.println(cell.getDateCellValue());
                    } else {
                        System.out.println((long) cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    System.out.println(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    System.out.println(cell.getCellFormula());
                    break;
                default:
                    System.out.println();
            }
	        }
	        System.out.println("");
	    }
	    file.close();
	
	     
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	}
}

