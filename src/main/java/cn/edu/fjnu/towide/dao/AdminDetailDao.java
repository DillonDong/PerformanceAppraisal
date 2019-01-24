package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.AdminDetails;
import cn.edu.fjnu.towide.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDetailDao {

//	@Select("SELECT u.password,ad.* FROM users as u INNER JOIN admin_details as ad ON u.username=ad.username WHERE u.username=#{userName}")
//	User getAdminDetailsByUserName(String userName);
	@Select("SELECT u.username,u.password,u.enabled,u.verification_code,ad.* " +
			"FROM " +
			"users AS u " +
			"LEFT JOIN admin_details AS ad ON u.username=ad.username " +
			"WHERE u.username=#{userName}")
	User getAdminDetailsByUserName(String userName);

	/**
	 *  带groupId的管理员模糊查询
	 */
	@Select("<script>"
			+ "SELECT u.username,u.enabled,ad.* "
			+ "FROM admin_details AS ad "
			+ "LEFT JOIN users AS u ON u.username=ad.username "
			+ "LEFT JOIN group_members AS gm ON ad.username=gm.username "
			+ "WHERE ad.username NOT LIKE #{currentUserName} "
			+ "<if test=\"partUserName!=null and partUserName!=\'\' \"> "
			+ "AND ad.username like concat('%', #{partUserName}, '%')  "
			+ "</if> "
			+ "<if test=\"partRealName!=null and partRealName!=\'\' \"> "
			+ "AND ad.real_name like concat('%', #{partRealName}, '%') "
			+ "</if> "
			+ "<if test=\"partEmail!=null and partEmail!=\'\' \"> "
			+ "AND ad.email like concat('%', #{partEmail}, '%') "
			+ "</if> "
			+ "<if test=\"partPhone!=null and partPhone!=\'\' \"> "
			+ "AND ad.phone like concat('%', #{partPhone}, '%') "
			+ "</if> "
			+ "<if test=\"groupId!=null and groupId!=\'\' \"> "
			+ "AND gm.group_id =#{groupId} "
			+ "</if> "
			+ "</script>")
	List<AdminDetails> fuzzyQueryByGroupIdAndPartAdminInfoFromUsersAndAdminDetails(
			@Param("partUserName") String partUserName,
			@Param("partRealName") String partRealName,
			@Param("partEmail") String email,
			@Param("partPhone") String partPhone,
			@Param("groupId") Integer groupId,
			@Param("currentUserName") String currentUserName);

	/**
	 * @param currentUserName
	 *  根据部分用户名、真实姓名、手机号模糊查询用户
	 * @param
	 */
	@Select("<script>"
			+ "SELECT u.username,u.enabled,ad.* "
			+ "FROM admin_details AS ad "
			+ "LEFT JOIN users AS u ON u.username=ad.username "
			+ "WHERE ad.username NOT LIKE #{currentUserName} "
			+ "<if test=\"partUserName!=null and partUserName!=\'\' \"> "
			+ "AND ad.username like concat('%', #{partUserName}, '%')  "
			+ "</if> "
			+ "<if test=\"partRealName!=null and partRealName!=\'\' \"> "
			+ "AND ad.real_name like concat('%', #{partRealName}, '%') "
			+ "</if> "
			+ "<if test=\"partPhone!=null and partPhone!=\'\' \"> "
			+ "AND ad.phone like concat('%', #{partPhone}, '%') "
			+ "</if> "
			+ "<if test=\"partEmail!=null and partEmail!=\'\' \"> "
			+ "AND ad.email like concat('%', #{partEmail}, '%') "
			+ "</if> "
			+ "</script>")
	List<AdminDetails> fuzzyQueryByPartAdminInfoFromUsersAndAdminDetails(
			@Param("partUserName") String partUserName,
			@Param("partRealName") String partRealName,
			@Param("partPhone") String partPhone,
			@Param("partEmail") String partEmail,
			@Param("currentUserName") String currentUserName);
}
