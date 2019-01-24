package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.User;
import cn.edu.fjnu.towide.entity.UserDetails;
import cn.edu.fjnu.towide.vo.UserInfoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserDao {




	@Update("UPDATE users SET verification_code=#{verificationCode} WHERE username=#{userName}")
	boolean updateVerificationCodeByUserName(@Param("userName") String userName,
											 @Param("verificationCode") String verificationCode);

	@Select("SELECT u.username,u.password,u.verification_code,ud.* " + "FROM " + "users as u "
			+ "LEFT JOIN user_details as ud ON u.username=ud.username "
			+ "WHERE (u.username=#{searchCondition})or(ud.phone=#{searchCondition})")
	User getUserByUserNameOrPhone(String searchCondition);



	@Update("UPDATE users SET password=#{password} WHERE username=#{username}")
	boolean setPassword(@Param("username") String username, @Param("password") String password);



	/**
	 *  修改用户使用状态，启用或停用
	 */
	@Update("UPDATE users SET enabled=#{stateConstant} WHERE username=#{userName}")
	boolean modifyUserStatus(@Param("userName") String userName, @Param("stateConstant") Integer stateConstant);

	// 修改Users表密码
	@Update("UPDATE users SET password=#{passwordNew} WHERE username=#{username}")
	boolean updatePasswordForUsers(@Param("passwordNew") String passwordNew, @Param("username") String username);




	/*用户注册*/
 
	//根据身份证号获取邮箱
	@Select("SELECT email FROM user_details WHERE id_card_num=#{idCardNum}")
	String getEmailFromIdCardNum(String idCardNum);

	//根据身份证号获取用户名称
	@Select("SELECT username FROM user_details WHERE id_card_num=#{idCardNum}")
	String getUsernameFromIdCardNum(String idCardNum);



	@Insert("INSERT INTO tb_bmxx (bmh) VALUES (#{username})")
	boolean addUserTobmxx(String username);


	/**
	 * 前台添加用户信息  如果已存在则更新
	 * @return 添加是否成功
	 */
	@Insert("REPLACE INTO users (username,password,enabled) VALUES (#{username},#{password},#{enabled})")
	boolean addUsers(User user);

	/**
	 *  前台添加用户信息user_details   如果已存在则更新
	 */
	@Insert("REPLACE INTO user_details(username,nickname,head,province,city) "
			+"VALUES (#{username},#{nickname},#{head},#{province},#{city})")
	boolean addUserDetails(UserDetails userDetails);

	@Update("UPDATE user_details SET update_time=#{update_time} WHERE username=#{username}")
	boolean updateTime(@Param("update_time")Date update_time, @Param("username") String username);

	//根据用户名得到更新时间
	@Select("SELECT update_time FROM user_details WHERE username=#{username}")
	Date getUpdateTimeFromUsername(String username);

	//查重
	@Select("SELECT COUNT(1) FROM user_details WHERE username=#{username}")
	boolean isUserExist(String username);
}
