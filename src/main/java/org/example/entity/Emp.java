package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工实体类，通过 MyBatis-Plus 注解映射到数据库表 emp。
 * <p>
 * 与 dept 表存在多对一关系：多个员工属于同一个部门，通过 dept_id 外键关联。
 */
@TableName("emp")
public class Emp {

    /** 员工编号（主键，自增） */
    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;
    /** 姓名 */
    private String empName;
    /** 性别 */
    private String gender;
    /** 部门名称（冗余字符串列，为兼容已有查询而保留） */
    private String dept;
    /** 部门编号（外键 -> dept.dept_id） */
    private Integer deptId;
    /** 岗位 */
    private String post;
    /** 薪资 */
    private BigDecimal salary;
    /** 入职时间 */
    private Date hireDate;
    /** 状态：1 在职 0 离职 */
    private Integer status;

    /**
     * 多对一关联：所属部门对象。
     * 该属性不是 emp 表的列，必须标记 exist = false，否则 MyBatis-Plus 会把它当作字段拼进 SQL。
     */
    @TableField(exist = false)
    private Dept deptInfo;

    /** 无参构造（MyBatis 反射实例化时需要） */
    public Emp() {
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Dept getDeptInfo() {
        return deptInfo;
    }

    public void setDeptInfo(Dept deptInfo) {
        this.deptInfo = deptInfo;
    }

    /**
     * 注意：此处只输出 deptInfo 的部门名，而不是整个 Dept 对象。
     * 因为 Dept 的 toString 会输出 emps 列表，若两者互相完整输出会形成循环引用导致栈溢出。
     */
    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", dept='" + dept + '\'' +
                ", deptId=" + deptId +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", status=" + status +
                ", deptInfo=" + (deptInfo == null ? "null" : deptInfo.getDeptName()) +
                '}';
    }
}