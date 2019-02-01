package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.AssessmentItem;
import cn.edu.fjnu.towide.xxx.assessmentItemManagement.vo.CountVo;
import cn.edu.fjnu.towide.xxx.deptManagement.vo.AssessmentItemVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxxin
 * @date 2019-01-27 16:24
 */
@Repository
public interface AssessmentItemDao {


    @Insert("REPLACE INTO assessment_item(id,name) VALUES (#{id},#{name})")
    Boolean AddAssessmentItem(AssessmentItem assessmentItem);

    @Select("<script>" +
            "SELECT * FROM assessment_item WHERE 1=1" +
            "<if test='name!=null'>" +
            " AND name like CONCAT('%',#{name},'%')" +
            "</if>" +
            "</script>")
    List<AssessmentItem> getAssessmentItemList(@Param("name")String name);


    @Select("<script>"
            +   "SELECT COUNT(*) FROM dept_as WHERE as_id IN "
            +   "<foreach collection='asId' item='id' open='(' close=')' separator=','>"
            +       "#{id}"
            +   "</foreach>"
            + "</script>")
    Long GetAssessmentItemNumberByAsId(@Param("asId") List<String> asId);

    @Delete("<script>"
            +   "DELETE FROM assessment_item WHERE id IN "
            +   "<foreach collection='asId' item='id' open='(' close=')' separator=','>"
            +       "#{id}"
            +   "</foreach>"
            + "</script>")
    Boolean DeleteAssessmentItemByAsId(@Param("asId") List<String> asId);


    @Delete("DELETE FROM count WHERE as_id = #{asId}")
    void DeleteCountByAsId(String asId);

    @Insert("<script>" +
            "   INSERT INTO count (as_id,level,count) " +
            "   VALUES" +
            "   <foreach collection='list' item='item' separator=','>" +
            "       (#{as_id},#{item.level},#{item.count})" +
            "   </foreach>" +
            "</script>")
    Boolean InsertAssessmentItemCount(@Param("list")List<CountVo> list, @Param("as_id")String asId);

    @Select("SELECT level,count FROM count WHERE as_id = #{asId}")
    List<CountVo> GetAssessmentItemLevelAndCountByAsId(String asId);

    @Select("SELECT * FROM assessment_item WHERE id = #{asId}")
    AssessmentItem GetAssessmentItemByAsId(String asId);
}
