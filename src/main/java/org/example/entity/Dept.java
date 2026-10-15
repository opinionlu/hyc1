package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

/**
 * 部门实体类，通过 MyBatis-Plus 注解映射到数据库表 dept。
 * <p>
 * 与 emp 表存在一对多关系：一个部门下有多个员工。
 */
@TableName("dept")
public class Dept {

    /** 部门编号（主键，自增） */
    @TableId(value = "dept_id", type = IdType.AUTO)
    private Integer deptId;
    /** 部门名称 */
    private String deptName;
    /** 办公地点 */
    private String location;
    /** 部门负责人 */
    private String manager;

    /**
     * 一对多关联：该部门下的所有员工。
     * 该属性不是 dept 表的列，必须标记 exist = false。
     */
    @TableField(exist = false)
    private List<Emp> emps;

    /** 无参构造（MyBatis 反射实例化时需要） */
    public Dept() {
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<Emp> getEmps() {
        return emps;
    }

    public void setEmps(List<Emp> emps) {
        this.emps = emps;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", location='" + location + '\'' +
                ", manager='" + manager + '\'' +
                ", emps=" + emps +
                '}';
    }
}