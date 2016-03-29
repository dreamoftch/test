package com.tch.test.iwjw.march;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import com.tch.test.common.date.DateUtils;

/**
	表需要迁移的 ，迁移方案半年一次 。
	rent_check_record (publishTime )
	sell_check_record (publishTime )
	houseresourceviewhistory (createTime)
	按年份分表,保存2014年数据，及2015年上半年数据，然后删除原表中相同的数据
	rent_check_record_2014
	rent_check_record_2015
	sell_check_record_2014
	sell_check_record_2015
	houseresourceviewhistory_2014
	houseresourceviewhistory_2015
 */
public class GenerateBackupSQL {
	
	private static int years[] = {2014, 2015};
	
	private static String tables[] = {"rent_check_record", "sell_check_record", "houseresourceviewhistory"};
	
	private static String[] fields = {
					"historyId, actionType, afterCheckHouseState, checkNum, createTime, entrustDate, houseId, houseState, note, "
					+ "operatorId, publishDate, remark, rentFreeDate, rentPrice, resultDate, spaceArea, stateReason, status, userId, "
					+ "balconySum, bedroomSum, livingRoomSum, wcSum, rentTermDate, source, rentDate, checkFaildType, checkTaskId, "
					+ "contactPublishId, bindMobile, bindMobileExt, landlordId, cityId, estateId, districtId, townId "
					,
					"id, publishId, createTime, modifyTime, operatorId, status, resultTime, checkNum, note, houseId, actionType, cityId, "
					+ "estateId, districtId, townId, houseState, houseStateAfter, publishTime, checkFaildType"
					,
					"id, userId, createTime, houseId, provinceId, cityId, townId, estateId, bedroomSum, livingRoomSum, wcSum, spaceArea, houseState, price, publishTime, phoneModel, os"
					
	};
	
	private static String sqlFormat = 
			"insert into  " +
			"%s " +
			"( " +
			"%s " +
			") " +
			"select  " +
			"%s " +
			" from %s " +
			"where %s >= '%s' and %s < '%s';";
	
	private static String[] publishDates = {"publishDate", "publishTime", "publishTime"};

	public static void main(String[] args) throws IOException {
		validate();
		for(int i=0;i<tables.length; i++){
			for(int year : years){
				//根据表明和年份处理
				handleByTableAndYear(tables[i], year, publishDates[i], fields[i]);
			}
		}
	}
	
	private static void validate(){
		if(tables.length != fields.length || tables.length != publishDates.length){
			throw new IllegalStateException("长度不一致");
		}
	}
	
	private static File getStoreFile(String newTable) throws IOException{
		File file = new File("D:\\工作\\备份数据\\insert_" + newTable + ".txt");
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		return file;
	}
	
	/**
	 * 根据表明和年份处理
	 * @param table
	 * @param year
	 * @param publishDate
	 * @param field
	 * @throws IOException
	 */
	public static void handleByTableAndYear(String table, int year, String publishDate, String field) throws IOException{
		String newTable = table + "_" + year;//存储的表名
		File file = getStoreFile(newTable);//根据表明获取对应的file
		FileWriter writer = new FileWriter(file);
		try {
			//取得该年份的第一天的起始时间
			Calendar cal = DateUtils.getStartTimeOfYear(year);
			while(cal.get(Calendar.YEAR) == year){
				//生成指定范围的sql
				createSQL(cal, year, table, newTable, publishDate, field, writer);
				writer.write("\r\n");
				writer.write("select SLEEP(2); ");
				writer.write("\r\n");
			}
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	/**
	 * 根据参数生成指定范围的sql
	 * @param cal
	 * @param year
	 * @param table
	 * @param newTable
	 * @param publishDate
	 * @param field
	 * @param writer
	 * @throws IOException
	 */
	public static void createSQL(Calendar cal, int year, String table, String newTable, String publishDate, String field, FileWriter writer) throws IOException{
		String from = DateUtils.formatDate(cal.getTime());
		String to = getEndTime(cal, year);
		String sql = String.format(sqlFormat, newTable, field, field, table, publishDate, from, publishDate, to);
		writer.write(sql);
		System.out.println(sql);
	}
	
	private static String getEndTime(Calendar cal, int year){
		String to = null;
		moveTwoDays(cal);//往后挪2天
		if(cal.get(Calendar.YEAR) == year){
			//没有超过当年
			to = DateUtils.formatDate(cal.getTime());
		}else{
			//跨过了当年，取下一年的第一天
			cal = DateUtils.getStartTimeOfYear(year + 1);
			to = DateUtils.formatDate(cal.getTime());
		}
		return to;
	}
	
	public static void moveTwoDays(Calendar cal){
		cal.add(Calendar.DAY_OF_MONTH, 2);
	}
	
}
