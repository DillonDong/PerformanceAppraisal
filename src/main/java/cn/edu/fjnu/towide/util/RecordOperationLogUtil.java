/**  
 * @Title: RecordOperationLogUtil.java  
 * @Package cn.edu.fjnu.towide.bean
 * @author CaoZhengxi  
 * @date 2018年5月2日  
 */
package cn.edu.fjnu.towide.util;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.fjnu.towide.dao.OperationLogDao;
import cn.edu.fjnu.towide.entity.OperationLog;
import cn.edu.fjnu.towide.entity.User;

/**
 * @ClassName: RecordOperationLogUtil
 *  记录操作日志
 * @author CaoZhengxi
 *  2018年5月2日
 */
@Component
public class RecordOperationLogUtil {

	@Autowired
	private OperationLogDao logDao;

	private static OperationLogDao operationLogDao;

	@PostConstruct
	public void init() {
		operationLogDao=logDao;
	}
	
	/**
	 *  存储admin日志
	 */
	public static void recordAdminOperationLog(User user,String examinee, String operationContent, String operationResult,
											   String processResultDescription) {

		Date operationTime = new Date();
		OperationLog operationLog = new OperationLog();
//		operationLog.setId(IdGenerator.getId());
		operationLog.setUsername(user.getUsername());
//		operationLog.setRealName(user.getRealName());
//		operationLog.setNickName(user.getNickname());
		operationLog.setIp(user.getIp());
		operationLog.setExaminee(examinee);
		operationLog.setOperationContent(operationContent);
		operationLog.setOperationDateTime(operationTime);
		operationLog.setOperationResult(operationResult);
		operationLog.setReason(processResultDescription);

		operationLogDao.addAdminOperationLog(operationLog);
	}
	/**
	 *  存储admin日志
	 */
	public static void recordAdminOperationLog(User user, String operationContent, String operationResult,
											   String processResultDescription) {

		Date operationTime = new Date();
		OperationLog operationLog = new OperationLog();
//		operationLog.setId(IdGenerator.getId());
		operationLog.setUsername(user.getUsername());
//		operationLog.setRealName(user.getRealName());
//		operationLog.setNickName(user.getNickname());
		operationLog.setIp(user.getIp());
		operationLog.setOperationContent(operationContent);
		operationLog.setOperationDateTime(operationTime);
		operationLog.setOperationResult(operationResult);
		operationLog.setReason(processResultDescription);

		operationLogDao.addAdminOperationLog(operationLog);
	}

	/**
	 *  存储user日志
	 */
	public static void recordUserOperationLog(User user, String operationContent, String operationResult,
											   String processResultDescription) {


		Date operationTime = new Date();
		OperationLog operationLog = new OperationLog();
//		operationLog.setId(IdGenerator.getId());
		operationLog.setUsername(user.getUsername());
//		operationLog.setRealName(user.getRealName());
//		operationLog.setNickName(user.getNickname());
		operationLog.setIp(user.getIp());
		operationLog.setOperationContent(operationContent);
		operationLog.setOperationDateTime(operationTime);
		operationLog.setOperationResult(operationResult);
		operationLog.setReason(processResultDescription);

		operationLogDao.addUserOperationLog(operationLog);
	}
}