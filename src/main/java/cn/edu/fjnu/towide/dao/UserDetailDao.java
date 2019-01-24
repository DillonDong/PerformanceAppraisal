package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.User;
import cn.edu.fjnu.towide.entity.UserDetails;
import cn.edu.fjnu.towide.vo.UserInfoVo;
import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

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
     *  通过openId获取用户信息
     */
    @Select("SELECT username,nickname,head,province,city FROM user_details WHERE username =#{openId}")
    UserInfoVo getUserInfoByOpenId(String openId);


    /**
     *  存储用户信息
     */
    @Insert("INSERT INTO user(username,nickname,head,province,city) "
            +"VALUES (#{username},#{nickname},#{head},#{province},#{city})")
    boolean addUserInfo(UserInfoVo userInfoVo);

    // @Insert("REPLACE user_details(username,phone,email,gender,nickname,birthday,last_update_time) VALUES (#{username},#{phone},#{email},#{sex},#{nickname},#{birthday},#{updateTime})")
    // boolean updateUserInformation(UserDetails userDetails);

    @Update("UPDATE user_details set phone=#{phone},email=#{email},gender=#{sex},nickname=#{nickname},birthday=#{birthday},last_update_time=#{updateTime} where username=#{username}")
    boolean updateUserInformation(UserDetails userDetails);

}
