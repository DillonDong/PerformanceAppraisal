package cn.edu.fjnu.towide.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import cn.edu.fjnu.towide.entity.User;

@Repository
public interface GroupMembersDao {

	/**  
	 * @Description: 删除用户
	 */  
	@Delete("DELETE FROM group_members WHERE username=#{username}")
	boolean deleteUserFromGroupMembers(String username);

	//查重
	@Select("SELECT COUNT(1) FROM group_members WHERE username=#{username}")
	int isGroupExist(@Param("username") String username);

	/**
	 * @Description: 添加用户
	 */
	@Insert("REPLACE INTO  group_members (username,group_id)"
			+ "values "
			+ "(#{userName},#{group_id}) ")
	boolean addGroupMembers(@Param("userName")String userName,@Param("group_id") Long group_id);

	/**  
	 * @Description: 添加用户
	 */  
	@Insert("<script>"
			+"INSERT INTO  group_members (username,group_id)"
			+ "values "
			+ "<foreach collection=\"groupIdList\" item=\"item\"  index=\"index\" separator=\",\"> "
			+ "(#{userName},#{item})" 
			+ "</foreach> "
			+ "</script>")
	boolean addPersonnelToGroupMembers(@Param("userName")String userName,@Param("groupIdList") List<Long> groupIdList);

	/**  
	 * @Description: 通过组id获取组成员列表
	 */  
	@Select("SELECT "
			+ "ud.username,ud.realname "
			+ "FROM group_members AS gm "
			+ "LEFT JOIN user_details AS ud ON gm.username=ud.username "
			+ "WHERE gm.group_id=#{id}")
	List<User> getGroupMemberByGroupId(Long id);

	/**  
	 * @Description: TODO
	 */  
	@Select("SELECT COUNT(group_members.group_id) FROM group_members WHERE group_id=#{groupId}")
	boolean checkMembersInGroup(long groupId);
    
}