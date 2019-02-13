package cn.edu.fjnu.towide.dao;

import cn.edu.fjnu.towide.entity.AssessmentItem;
import cn.edu.fjnu.towide.entity.Department;
import cn.edu.fjnu.towide.xxx.deptManagement.vo.AssessmentItemVo;
import cn.edu.fjnu.towide.xxx.deptManagement.vo.AssessmentItemWithWeightVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xxxin
 * @date 2019-01-27 23:05
 */
@Repository
public interface DepartmentDao {


    @Insert("REPLACE INTO department(id,name) VALUES (#{id},#{name})")
    Boolean AddDepartment(Department department);

    @Select("<script>" +
            "SELECT * FROM department WHERE 1=1" +
            "<if test='name!=null'>" +
            " AND name like CONCAT('%',#{name},'%')" +
            "</if>" +
            "</script>")
    List<Department> GetDepartmentList(@Param("name") String name);



    @Delete("DELETE FROM dept_as WHERE dept_id = #{deptId}")
    void DeleteAssessmentItemByDepartmentId(String deptId);



    @Insert("<script>" +
            "   INSERT INTO dept_as (dept_id,as_id,weight) " +
            "   VALUES" +
            "   <foreach collection='list' item='item' separator=','>" +
            "       (#{dept_id},#{item.asId},#{item.weight})" +
            "   </foreach>" +
            "</script>")
    Boolean InsertAssessmentItems(@Param("list")List<AssessmentItemVo> list, @Param("dept_id")String deptId);



    @Select("SELECT id,name,weight FROM dept_as LEFT JOIN assessment_item ON dept_as.as_id = assessment_item.id WHERE dept_id = #{deptId}")
    List<AssessmentItemWithWeightVo> GetAssessmentItemsByDepartmentId(String deptId);

    @Select("SELECT * FROM department WHERE id = #{deptId}")
    Department GetDepartmentByDepartmentId(String deptId);

    @Delete("<script>"
            +   "DELETE FROM dept_as WHERE dept_id IN "
            +   "<foreach collection='deptId' item='id' open='(' close=')' separator=','>"
            +       "#{id}"
            +   "</foreach>"
            + "</script>")
    void DeleteAssessmentItemByDeptId(@Param("deptId") List<String> deptId);


    @Delete("<script>"
            +   "DELETE FROM department WHERE id IN "
            +   "<foreach collection='deptId' item='id' open='(' close=')' separator=','>"
            +       "#{id}"
            +   "</foreach>"
            + "</script>")
    Boolean DeleteDepartmentByDeptId(@Param("deptId") List<String> deptId);

    @Select("SELECT Count(*) FROM department WHERE name = #{name}")
    Long CheckDeptName(String name);
}
