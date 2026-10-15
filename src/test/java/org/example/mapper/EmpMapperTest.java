package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Emp;
import org.example.util.MyBatisUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
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
            int rows = mapper.updateById(emp);
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

    @Test
    public void testSelectByCondition() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            // 条件1：按部门模糊查询（"研发" 匹配 "研发部"）
            Emp c1 = new Emp();
            c1.setDept("研发");
            List<Emp> byDept = mapper.selectByCondition(c1);
            Assert.assertFalse("按部门模糊查询不应为空", byDept.isEmpty());
            System.out.println("按部门[研发]模糊查询到 " + byDept.size() + " 条：");
            byDept.forEach(System.out::println);

            // 条件2：多条件（性别 + 状态）
            Emp c2 = new Emp();
            c2.setGender("女");
            c2.setStatus(1);
            List<Emp> byMulti = mapper.selectByCondition(c2);
            System.out.println("按性别[女]+状态[1]查询到 " + byMulti.size() + " 条：");
            byMulti.forEach(System.out::println);
        }
    }

    @Test
    public void testInsertBatch() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            Emp e1 = new Emp();
            e1.setEmpName("批量1"); e1.setGender("男"); e1.setDept("研发部"); e1.setStatus(1);
            Emp e2 = new Emp();
            e2.setEmpName("批量2"); e2.setGender("女"); e2.setDept("市场部"); e2.setStatus(1);

            int rows = mapper.insertBatch(Arrays.asList(e1, e2));
            session.commit();
            Assert.assertEquals(2, rows);
            System.out.println("批量插入 " + rows + " 条");
        }
    }

    @Test
    public void testDeleteBatchIds() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            // 先单条插入两条临时数据，拿到回填主键
            Emp e1 = new Emp();
            e1.setEmpName("待删1"); e1.setGender("男"); e1.setStatus(1);
            Emp e2 = new Emp();
            e2.setEmpName("待删2"); e2.setGender("女"); e2.setStatus(1);
            mapper.insert(e1);
            mapper.insert(e2);
            session.commit();

            int rows = mapper.deleteBatchIds(Arrays.asList(e1.getEmpId(), e2.getEmpId()));
            session.commit();
            Assert.assertEquals(2, rows);
            System.out.println("批量删除 " + rows + " 条");
        }
    }

    @Test
    public void testSelectByChoose() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            // 分支1：有姓名 → 按姓名精确查询
            Emp c1 = new Emp();
            c1.setEmpName("张伟");
            List<Emp> byName = mapper.selectByChoose(c1);
            Assert.assertFalse("按姓名查询不应为空", byName.isEmpty());
            System.out.println("分支1 按姓名[张伟]查询到 " + byName.size() + " 条：");
            byName.forEach(System.out::println);

            // 分支2：无姓名、有部门 → 按部门查询
            Emp c2 = new Emp();
            c2.setDept("研发部");
            List<Emp> byDept = mapper.selectByChoose(c2);
            Assert.assertFalse("按部门查询不应为空", byDept.isEmpty());
            System.out.println("分支2 按部门[研发部]查询到 " + byDept.size() + " 条：");
            byDept.forEach(System.out::println);

            // 分支3：姓名、部门都有 → 只按姓名（姓名优先，忽略部门）
            Emp c3 = new Emp();
            c3.setEmpName("张伟");
            c3.setDept("市场部");
            List<Emp> byPriority = mapper.selectByChoose(c3);
            Assert.assertEquals("姓名与部门同时存在时应只按姓名查询", byName.size(), byPriority.size());
            System.out.println("分支3 姓名[张伟]+部门[市场部]同时存在 → 只按姓名，查到 " + byPriority.size() + " 条");

            // 分支4：都为空 → 查询全部
            List<Emp> all = mapper.selectByChoose(new Emp());
            Assert.assertFalse("无条件查询不应为空", all.isEmpty());
            System.out.println("分支4 无条件 → 查询全部 " + all.size() + " 条");
        }
    }

    @Test
    public void testSelectByKeyword() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> list = mapper.selectByKeyword("研发");
            Assert.assertFalse("关键字检索不应为空", list.isEmpty());
            System.out.println("关键字[研发]匹配到 " + list.size() + " 条：");
            list.forEach(System.out::println);
        }
    }
}
