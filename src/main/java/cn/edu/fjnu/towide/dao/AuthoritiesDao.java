package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.Authority;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthoritiesDao {

	/**  
	 * @Description: 删除user
	 */  
	@Delete("DELETE FROM authorities WHERE username=#{userName}")
	boolean deleteUserFromAuthorities(String userName);

	/**  
	 * @param userName 
	 * @Description: 
	 */  
	@Insert("<script> "
			+"REPLACE  INTO  authorities (username,authority) "
			+ "values "
			+ "<foreach collection=\"authorityList\" item=\"item\"  index=\"index\" separator=\",\"> "
			+ "(#{userName},#{item}) "
			+ "</foreach> "
			+ "</script> ")
	boolean addPersonnelToAuthorities(@Param("userName") String userName,@Param("authorityList") List<String> authorityList);

	/**  
	 * 获取可用权限列表
	 */  
	@Select("SELECT * FROM authority_dd ")
	List<Authority> getPermissionsList();

	@Select("SELECT " + "ad.authority,ad.description "
			+ "FROM authorities as a "
			+ "LEFT JOIN  authority_dd as ad ON a.authority=ad.authority "
			+ "WHERE a.username =#{username}")
    List<Authority> loadAuthorityByUsername(String username);
}
