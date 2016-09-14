package com.tch.test.august;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWriteWorkbook {
	public static void main(String[] args) throws IOException {
		File myFile = new File("/home/tch/Downloads/8.18名单.xlsx");
		String filename = "/media/tch/disk1/study/temp/mobile.txt";
		String filename2 = "/media/tch/disk1/study/temp/mobile2.txt";
		File file = new File(filename);
		if(! file.exists()){
			file.createNewFile();
		}
		File file2 = new File(filename2);
		if(! file2.exists()){
			file2.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
		BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2, false)));
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		Iterator<Row> rowIterator = mySheet.rowIterator();
		int i = 0;
		while (rowIterator.hasNext()) {
			i++;
			Row row = rowIterator.next();
			Cell mobileCell = row.getCell(1);
			if(mobileCell != null){
				String mobile = null;
				switch (mobileCell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						mobile = mobileCell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						mobile = String.valueOf(mobileCell.getNumericCellValue());
						break;
				}
				if(i < 1000){
					writer.write(mobile);
					writer.write(",");
				}else{
					writer2.write(mobile);
					writer2.write(",");
				}
			}
		}
		writer.write("18521736087");
		writer.write(",");
		writer.write("18117027220");
		writer.write(",");
		writer.flush();
		writer.close();
		
		
		writer2.write(",");
		writer2.flush();
		writer2.close();
		System.out.println(i);
		myWorkBook.close();
	}
}
