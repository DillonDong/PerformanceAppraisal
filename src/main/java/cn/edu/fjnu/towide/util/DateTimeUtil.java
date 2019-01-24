package cn.edu.fjnu.towide.util;

import java.text.SimpleDateFormat;
import java.util.Date;



public class DateTimeUtil {
	public static final int ms = 1000;
	public static final int limitTimeSeconds = 2 * 24 * 60 * 60;
	
	public static String getCurrentDataTimeString(){
		Date currentDataTime=new Date();
		String currentDataTimeString=getDateTimeString(currentDataTime);
		return currentDataTimeString;
	}

	public static String getTimeIntervalStringByFormatHourSecondMinute(Date startTime, Date endTime) {
		long startTimeLong=startTime.getTime();
		long endtimeLong=endTime.getTime();
		long timeIntervalLong=(endtimeLong-startTimeLong)/1000;
		
		long minute=timeIntervalLong%60;
		long second=(timeIntervalLong/60)%60;
		long hour=timeIntervalLong/3600;
		
		String timeInterval=hour+"时"+second+"分"+minute+"秒";
		return timeInterval;
	}

	public static String getDateTimeString(Date dateTime) {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTimeString=simpleDateFormat.format(dateTime);
		return dateTimeString;
	}
	
	/**
	 * 获取剩余时间
	 * @param questionDate	某会话最新的提问时间
	 * @return
	 */
	public static Integer getRestSeconds(Date questionDate) {
		Integer restSeconds = null;
		Date presentDate = new Date();
		if(questionDate != null) {
			long secondsLong = (presentDate.getTime() - questionDate.getTime()) / ms;
			restSeconds = limitTimeSeconds - (int)secondsLong;
		}
		return restSeconds;
	}
}
