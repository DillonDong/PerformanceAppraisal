package cn.edu.fjnu.towide.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import cn.edu.fjnu.towide.entity.OperationLog;
@Repository
public interface OperationLogDao {

	@Insert("INSERT INTO admin_log "
			+ "("
			+ "username,"
			+ "examinee,"
			+ "ip,operation_content,operation_date_time,operation_result,"
			+ "reason"
			+ ")VALUES("
			+ "#{username},"
			+ "#{examinee},"
			+ "#{ip},#{operationContent},#{operationDateTime},#{operationResult},"
			+ "#{reason}"
			+ ")")
	void addAdminOperationLog(OperationLog operationLog);

	@Select("select * from operation_log order by operation_date_time desc")
	List<OperationLog> getOperationLogList();

	@Select("<script>"
			+ "SELECT * FROM operation_log WHERE 1=1 "
			+ "AND (operation_date_time BETWEEN #{startTime} "
			+ "AND #{endTime} )"
			+ "<if test='userName!=null '> "
			+ "AND username LIKE #{userName} "
			+ "</if>"
			+ "<if test='operation_result!=null '> "
			+ "AND operation_result = #{operation_result} "
			+ "</if>"
			+ "<if test='operation_content!=null '> "
			+ "AND operation_content LIKE #{operation_content} "
			+ "</if>"
			+ "order by operation_date_time desc"
			+ "</script>")
	List<OperationLog> searchOperationLog(
			@Param("startTime")String startTime, 
			@Param("endTime")String endTime, 
			@Param("userName")String userName, 
			@Param("operation_result")String operation_result,
			@Param("operation_content")String operation_content
			);

	@Insert("INSERT INTO user_log "
			+ "("
			+ "username,"
			+ "ip,operation_content,operation_date_time,operation_result,"
			+ "reason"
			+ ")VALUES("
			+ "#{username},"
			+ "#{ip},#{operationContent},#{operationDateTime},#{operationResult},"
			+ "#{reason}"
			+ ")")
	void addUserOperationLog(OperationLog operationLog);



	@Select("<script>"
			+ "SELECT * FROM user_log WHERE 1=1 "
			+ "AND (operation_date_time BETWEEN #{startTime} "
			+ "AND #{endTime} )"
			+ "<if test='ip!=null '> "
			+ "AND ip LIKE #{ip} "
			+ "</if>"
			+ "<if test='userName!=null '> "
			+ "AND username LIKE #{userName} "
			+ "</if>"
			+ "<if test='result!=null '> "
			+ "AND operation_result LIKE #{result} "
			+ "</if>"
			+ "<if test='content!=null '> "
			+ "AND operation_content LIKE #{content} "
			+ "</if>"
			+ "order by operation_date_time desc"
			+ "</script>")
	List<OperationLog> searchUserOperationLog(
			@Param("startTime")String startTime,
			@Param("endTime")String endTime,
			@Param("ip")String ip,
			@Param("userName")String userName,
			@Param("result")String result,
			@Param("content")String content
	);



	@Select("<script>"
			+ "SELECT * FROM admin_log WHERE 1=1 "
			+ "AND (operation_date_time BETWEEN #{startTime} "
			+ "AND #{endTime} )"
			+ "<if test='ip!=null '> "
			+ "AND ip LIKE #{ip} "
			+ "</if>"
			+ "<if test='userName!=null '> "
			+ "AND username LIKE #{userName} "
			+ "</if>"
			+ "<if test='result!=null '> "
			+ "AND operation_result LIKE #{result} "
			+ "</if>"
			+ "<if test='content!=null '> "
			+ "AND operation_content LIKE #{content} "
			+ "</if>"
			+ "order by operation_date_time desc"
			+ "</script>")
	List<OperationLog> searchAdminOperationLog(
			@Param("startTime")String startTime,
			@Param("endTime")String endTime,
			@Param("ip")String ip,
			@Param("userName")String userName,
			@Param("result")String result,
			@Param("content")String content
	);
}