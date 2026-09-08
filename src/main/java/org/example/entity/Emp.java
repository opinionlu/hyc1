package org.example.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工实体类，对应数据库表 emp。
 * 数据库下划线列名（如 emp_id、emp_name）通过 mapUnderscoreToCamelCase 自动映射为驼峰字段。
 */
public class Emp {

    /** 员工编号（主键，自增） */
    private Integer empId;
    /** 姓名 */
    private String empName;
    /** 性别 */
    private String gender;
    /** 部门 */
    private String dept;
    /** 岗位 */
    private String post;
    /** 薪资 */
    private BigDecimal salary;
    /** 入职时间 */
    private Date hireDate;
    /** 状态：1 在职 0 离职 */
    private Integer status;

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

    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", dept='" + dept + '\'' +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", status=" + status +
                '}';
    }
}
