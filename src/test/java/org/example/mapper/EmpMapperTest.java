package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Emp;
import org.example.util.MyBatisUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * EmpMapper 的 JUnit 单元测试。
 * 每个测试方法内部通过 try-with-resources 自行开启/关闭 SqlSession，互不共享状态。
 */
public class EmpMapperTest {

    @Test
    public void testSelectById() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            Emp emp = session.getMapper(EmpMapper.class).selectById(1);
            Assert.assertNotNull(emp);
            System.out.println(emp);
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            List<Emp> list = session.getMapper(EmpMapper.class).selectAll();
            Assert.assertFalse(list.isEmpty());
            list.forEach(System.out::println);
        }
    }

    @Test
    public void testInsert() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpName("陈晨");
            emp.setGender("女");
            emp.setDept("研发部");
            emp.setPost("测试工程师");
            emp.setSalary(new BigDecimal("9000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);

            int rows = mapper.insert(emp);
            session.commit(); // 增删改必须提交事务
            Assert.assertEquals(1, rows);
            System.out.println("回填主键 empId = " + emp.getEmpId());
        }
    }

    @Test
    public void testUpdate() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = mapper.selectById(1);
            emp.setSalary(new BigDecimal("13000"));
            int rows = mapper.update(emp);
            session.commit();
            Assert.assertEquals(1, rows);
        }
    }

    @Test
    public void testDelete() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            // 先插入一条临时数据，再删除，不依赖固定主键，保证测试可重复运行
            Emp emp = new Emp();
            emp.setEmpName("待删除");
            emp.setGender("男");
            emp.setStatus(1);
            mapper.insert(emp);
            session.commit();

            int rows = mapper.deleteById(emp.getEmpId());
            session.commit();
            Assert.assertEquals(1, rows);
        }
    }
}
