/**
 * @author CaoZhengxi
 * @date 2018年5月22日  
 */
package cn.edu.fjnu.towide.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @ClassName: CheckVariableUtil
 * @author CaoZhengxi
 * @date 2018年5月22日
 * 
 */
public class CheckVariableUtil {
	/**
	 * 检查分页信息非法
	 * @param pageNum 起始页
	 * @param pageSize 页面大小
	 * @return 如果分页信息非法返回true
	 */
	public static boolean pageParamIsIllegal(Integer pageNum, Integer pageSize) {

		return pageNum <= 0 || pageSize <= 0 || pageNum == null || pageSize == null;
	}

	/**
	 * 检测字符串是否存在非法字符
	 * @return 存在非法字符传返回true 非空返回false
	 */
	public static boolean charactersIsIllegal (String string,String check) {
		if(string.indexOf(check)!=-1){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 检测空字符串变量
	 * @return 空字符传返回true 非空返回false
	 */
	public static boolean stringVariableIsEmpty(String string) {
		if (string == null || "".equals(string.trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检测Integer值为null或小于0
	 * @return 如果为null或小于0返回true
	 */
	public static boolean integerParamLessZero(Integer value){
		return null==value || (value.intValue()<0);
	}

	/**
	 * 检测Integer值为null或小于0
	 * @return 如果为null或小于0返回true
	 */
	public static boolean longParamIsEmptyAndLessZero(Long value){
		return null==value || (value.intValue()<0);
	}

    /**
     * 检测字符串是Integer格式的
     * @return: 如果字符串 -不是- Integer格式的返回true
     */
    public static boolean variableIsInteger(String value) {
        if(value == null) {
            return true;
        }
        String pattern = "^[1-9]\\d*|0$";
        return !Pattern.matches(pattern, value);
    }


	/**
	 * 检测日期 YYYYMMDD是非法的
	 * @param date 日期
	 * @return 非法为true
	 */
    public static boolean dateIsIllegal(String date){
    	if (date==null || "".equals(date.trim()) ||  date.matches("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)")){
    		return true;
		}

		return false;
	}


	/**
	 * 判断时间格式是否是"yyyyMM"或者"yyyyMMdd"
	 * @param date   字符串类型时间
	 * @param format 时间格式
	 * @return 非法为true
	 */
	public static boolean dateIsIllegal(String date , String format){
		if(stringVariableIsEmpty(date))	return false;
		String month=date.substring(4,6);
		int mon = Integer.parseInt(month);
		if(mon<1||mon>12){
			return true;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date d =  sdf.parse(date); //把字符串转化为日期(可以过滤掉奇怪格式的日期格式)
			String newDate = sdf.format(d);//日期转化为字符

			/*
			 *1，把字符串的日期转化为Date类型的日期。
			 *2，把Date类型日期转化为字符串类型日期。
			 *3，如果两者相等，说明日期格式符合要求
			 */
			if(null != newDate && newDate.equals(date)){
				return false ;
			}else {
				return true ;
			}
		} catch (ParseException e) {
			//出现异常，说明时间格式有误
			return true ;
		}

	}

	/**
	 * 检测身份证号是非法的
	 * @param idCard
	 * @return非法返回true
	 */
	public static boolean isIdcardIllegal(String idCard) {

		//号码长度应为15位或18位
		if (idCard == null || "".equals(idCard.trim()) || (idCard.length() != 15 && idCard.length() != 18) || !idCard.matches("\\d{17}[\\d|X]")) {
			return true;
		}
		return false;
	}

	/**
	 * 检测身份证号是非法的
	 * @param iDCard
	 * @return非法返回true
	 */
	public static boolean iDCardIsIllegal(String iDCard){
		if (iDCard == null ||"".equals(iDCard.trim())|| iDCard.trim().length() != 18 || !iDCard.matches("\\d{17}[\\d|X]")){
			return true;
		}
		return false;
	}

	/**
	 * 检测 邮箱是非法的
	 * @param email
	 * @return 非法返回true
	 */
	public static boolean emailIsIllegal(String email){
		if (email==null || "".equals(email.trim())|| !email.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")){
			return true;
		}
		return false;
	}

	/**
	 * 检测中国邮政编码是非法的
	 * @param postCode
	 * @return 非法返回true
	 */
	public static boolean postCodeIsIllegal(String postCode){
		if (postCode==null || "".equals(postCode.trim())|| !postCode.matches("[1-9]\\d{5}(?!\\d)")){
			return true;
		}
		return false;
	}

	/**
	 * 检测手机号是非法的
	 * @param phoneNum
	 * @return 非法返回true
	 */
	public static boolean phoneIsIllegal(String phoneNum){
		if (phoneNum == null || "".equals(phoneNum.trim()) || phoneNum.trim().length() != 11 || !phoneNum.matches("\\d{11}")){
			return true;
		}
		return false;
	}

	/**
	 * 字符串转Double
	 * @param str
	 * @param defaultNum 默认值
	 * @return
	 */
	public static Double parseDouble(String str,double defaultNum) {
		double num=defaultNum;
		if (!stringVariableIsEmpty(str)) {
			try {
				num=Double.parseDouble(str.trim());
			} catch (Exception e) {
				num=defaultNum;
			}
		}
		return num;
	}

	/**
	 * 字符串转Integer
	 * @param str
	 * @param defaultNum 默认值
	 * @return
	 */
	public static Integer parseInt(String str,int defaultNum) {
		int num=defaultNum;
		if (!stringVariableIsEmpty(str)) {
			try {
				num=Integer.parseInt(str.trim());
			} catch (Exception e) {
				num=defaultNum;
			}
		}
		return num;
	}


	/**
	 * 字符串转Long
	 * @param str
	 * @param defaultNum 默认值
	 * @return
	 */
	public static Long parseLong(String str,Long defaultNum) {
		Long num=defaultNum;
		if (!stringVariableIsEmpty(str)) {
			try {
				num=Long.parseLong(str.trim());
			} catch (Exception e) {
				num=defaultNum;
			}
		}
		return num;
	}

}
