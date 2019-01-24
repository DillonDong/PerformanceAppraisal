package cn.edu.fjnu.towide.dao;

import java.util.List;

import cn.edu.fjnu.towide.entity.Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao {

	/**
	 *  获取组列表
	 */
	@Select("SELECT * FROM groups")
	List<Group> getGroupList();

	/**
	 *  删除组
	 */
	@Delete("DELETE FROM groups WHERE groups.id=#{groupId}")
	boolean deleteGroupByGroupId(Long groupId);

	/**
	 *  从groups中选取最大的id
	 */
	@Select("SELECT MAX(id) FROM groups")
	long selectMaxGroupId();

	/**
	 *  添加组到groups
	 */
	@Insert("INSERT INTO groups (id ,group_name ,description, remark) VALUES (#{id},#{groupName},#{description},#{remark})")
	boolean addGroup(@Param("id") long id, @Param("groupName") String groupName,
			@Param("description") String description, @Param("remark") String remark);

	/**
	 *  更新groups
	 */
	@Update("UPDATE groups " 
			+ "SET group_name=#{groupName},description=#{description},remark = #{remark} "
			+ "WHERE groups.id=#{groupId}")
	boolean updateGroup(@Param("groupId")Long groupId, @Param("groupName")String groupName, @Param("description")String description, @Param("remark")String remark);

	/**  
	 *  查找group name
	 */  
	@Select("SELECT group_name FROM groups WHERE id=#{groupId}")
	String selectGroupNameByGtoupId(Long groupId);

	/**  
	 *  得到组的数量
	 */  
	@Select("SELECT COUNT(*) FROM groups")
	long getGroupAmount();

}