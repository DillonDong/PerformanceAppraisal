package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.Authority;
import cn.edu.fjnu.towide.entity.GroupAuthorities;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupAuthoritiesDao {

	/**  
	 * @return 
	 * @Description: 通过组的id获取组的权限列表
	 */  
	@Select("SELECT "
			+ "ga.authority,ad.description  "
			+ "FROM group_authorities AS ga "
			+ "LEFT JOIN authority_dd AS ad ON ga.authority=ad.authority "
			+ "WHERE ga.group_id=#{id}")
	List<GroupAuthorities> getAuthoritiesByGroupId(Long id);

	/**  
	 * @Description: 添加权组
	 */ 
	@Insert("INSERT INTO group_authorities (group_id ,authority) VALUES (#{id},#{permission})")
	boolean addAuthorityGroup(@Param("id")long id,@Param("permission") String permission);

	/**  
	 * @Description: 根据id删除组的权限
	 */  
	@Delete("DELETE FROM group_authorities WHERE group_authorities.group_id=#{groupId}")
	boolean deleteGroupAuthoritiesByGroupId(Long groupId);
	
	/**  
	 * @return 
	 * @Description: 通过组的id获取组的权限列list
	 */  
	@Select("SELECT authority  "
			+ "FROM group_authorities  "
			+ "WHERE "
			+ "group_id=#{id}")
	List<String> getAuthorityColumnByGroupId(Long id);

	@Select("SELECT ga.authority,ad.description " +
			"FROM groups AS g, group_members AS gm, group_authorities AS ga ,authority_dd AS ad " +
			"WHERE  g.id = ga.group_id " +
			"AND g.id = gm.group_id " +
			"AND ga.authority=ad.authority " +
			"AND gm.username = #{username}")
    List<Authority> loadGroupAuthorityByUsername(String username);
}