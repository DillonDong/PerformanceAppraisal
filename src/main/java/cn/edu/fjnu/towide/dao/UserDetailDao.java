package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.*;
import cn.edu.fjnu.towide.vo.CountVo;
import cn.edu.fjnu.towide.vo.DeptAsVo;
import cn.edu.fjnu.towide.vo.UserDetailInfoVo;
import cn.edu.fjnu.towide.vo.UserInfoVo;
import org.apache.ibatis.annotations.*;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserDetailDao {


    @Select("SELECT username FROM user_details WHERE phone =#{phone}")
    String getUsernameByPhone(String phone);

    @Select("SELECT u.username,u.password,u.enabled,u.verification_code,ud.* " +
            "FROM " +
            "users AS u " +
            "LEFT JOIN " +
            "user_details AS ud ON u.username=ud.username WHERE u.username=#{useName}")
    User getUserDetailsByUserName(String userName);

    /**
     *  获取用户薪资信息
     */
    @Select("<script>"+"SELECT * FROM user_wages WHERE user_id =#{username} "
            + "<if test=\"startTime!=null and startTime!=\'\' \"> "
            + "AND time <![CDATA[  >=  ]]> #{startTime}  " + "</if> "
            + "<if test=\"endTime!=null and endTime!=\'\' \"> "
            + "AND time <![CDATA[  <=  ]]> #{endTime}  " + "</if> "
            + "order by time Desc "
            + "</script>")
    List<UserWages> getUserInfoByUsername(@Param("startTime") String startTime,
                                          @Param("endTime") String endTime, @Param("username")String username);


    /**
     *  获取用户项目考核
     */
    @Select("SELECT ai.id,ai.name "
            + "FROM user_details as u "
            + "LEFT JOIN dept_as as da ON u.dept_id=da.dept_id "
            + "LEFT JOIN assessment_item as ai ON da.as_id=ai.id "
            + "WHERE u.username=#{userName}"
    )
    List<AssessmentItem> getUserExaminationItemsInAdd(String userName);



    /**
     *  在详情中获取用户项目考核
     */
    @Select("SELECT * "
            + "FROM user_assessment "
            + "WHERE user_id=#{userName} and time=#{time}"
    )
    List<UserAssessment> getUserExaminationItemsInGet(@Param("userName")String userName , @Param("time")String time);



    /**
     * @Description: 删除用户薪资
     */
    @Delete("DELETE FROM user_wages WHERE user_id=#{username} and time=#{time} and examine=0")
    boolean deleteUserSalaryInfo(@Param("username")String username, @Param("time")String time);

    /**
     * @Description: 删除用户考核项
     */
    @Delete("DELETE FROM user_assessment WHERE user_id=#{username} and time=#{time}")
    boolean deleteUserExaminationItems(@Param("username")String username, @Param("time")String time);


    /**
     * @Description: 获取用户信息列表
     */
    @Select("<script>"+"SELECT username,real_name,remarks,base_salary,code,d.name "
            + "FROM user_details as u "
            + "LEFT JOIN department as d ON u.dept_id=d.id "
            + "WHERE 1=1 "
            + "<if test=\"keyword!=null and keyword!=\'\' \"> "
            + "AND real_name like concat('%', #{keyword}, '%') "+ "</if> "
            +"</script>"
    )
    List<UserInfoVo> getUserInfoList(@Param("keyword") String keyword);


    /**
     * @Description: 获取用户详细信息
     */
    @Select("SELECT username,real_name,remarks,base_salary,code,dept_id,id_pre,id_after "
            + "FROM user_details as u "
            + "WHERE username=#{username}"
    )
    UserDetailInfoVo getUserDetailedInfoList(String username);


    /**
     * @Description: 更新用户详细信息
     */
    @Update("UPDATE user_details SET base_salary=#{baseSalary},code=#{code},remarks=#{remarks},"
            +"dept_id=#{deptId},id_pre=#{idPre},id_after=#{idAfter} "
            +"WHERE username=#{username}")
    boolean updateUserDetailedInfo(UserDetailInfoVo p);




    /**
     * @Description: 找大于此水平的分数
     */
    @Select("SELECT min(level),min(count) "
            + "FROM count "
            + "WHERE level>=#{value} and as_id=#{itemId}"
    )
    CountVo getBiggerCount(@Param("itemId")String itemId, @Param("value")Double value);


    /**
     * @Description: 找小于此水平的分数
     */
    @Select("SELECT max(level),max(count) "
            + "FROM count "
            + "WHERE level<=#{value} and as_id=#{itemId}"
    )
    CountVo getSmallerCount(@Param("itemId")String itemId, @Param("value")Double value);


    /**
     * @Description:
     */
    @Select("SELECT name "
            + "FROM assessment_item "
            + "WHERE id=#{itemId}"
    )
    String getAssessmentItemName(String itemId);


    /**
     * @Description: 保存用户考核项
     */
    @Insert("REPLACE INTO user_assessment VALUES(" +
            "#{id},#{userId},#{assessmentItem},#{count},#{time})")
    boolean addAssessmentItemAndCount(UserAssessment userAssessment);


    /**
     * @Description:
     */
    @Select("SELECT dept_id "
            + "FROM user_details "
            + "WHERE username=#{username}"
    )
    String getDeptIdByUserName(String username);

    /**
     * @Description: 获取部门考核项列表
     */
    @Select("SELECT as_id,weight "
            + "FROM dept_as "
            + "WHERE dept_id=#{dept_id} "
    )
    List<DeptAsVo> getDeptAssessmentList(String dept_id);

    // @Insert("REPLACE user_details(username,phone,email,gender,nickname,birthday,last_update_time) VALUES (#{username},#{phone},#{email},#{sex},#{nickname},#{birthday},#{updateTime})")
    // boolean updateUserInformation(UserDetails userDetails);

    @Update("UPDATE user_details set phone=#{phone},email=#{email},gender=#{sex},nickname=#{nickname},birthday=#{birthday},last_update_time=#{updateTime} where username=#{username}")
    boolean updateUserInformation(UserDetails userDetails);

}
