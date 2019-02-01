package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.Parameter;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author xxxin
 * @date 2019-02-01 22:58
 */
@Repository
public interface ParameterDao {


    @Update("UPDATE parameter SET merits_pay=#{meritsPay},jxbl_left=#{jxblLeft},minimum_turnover=#{minimumTurnover},jxqj_left=#{jxqjLeft},jxqj_right=#{jxqjRight},ccqj_left=#{ccqjLeft},ccqj_middle=#{ccqjMiddle},ccqj_right=#{ccqjRight},ccqjzz_left=#{ccqjzzLeft},ccqjzz_right=#{ccqjzzRight},ccqjzy_left=#{ccqjzyLeft},ccqjzy_right=#{ccqjzyRight}")
    Boolean UpdateParameterRequest(Parameter parameter);

    @Select("SELECT * FROM parameter")
    Parameter GetParameterRequest();
}
