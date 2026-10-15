package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Dept;
import org.example.entity.Emp;
import org.example.util.MyBatisUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 多表查询（关联查询）单元测试。
 * 覆盖：一对多（collection）、多对一（association 嵌套结果）、多对一（分步查询 / 延迟加载）。
 * <p>
 * 注意：emp 表中可能存在由其他测试插入的、dept_id 为 null 的临时员工，
 * 这类员工在 LEFT JOIN 后 deptInfo 本就应为 null，因此断言只针对有部门的员工。
 */
public class JoinQueryTest {

    /** 一对多：查询所有部门及其下属员工 */
    @Test
    public void testSelectDeptWithEmps() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            List<Dept> depts = session.getMapper(DeptMapper.class).selectDeptWithEmps();

            Assert.assertFalse("部门列表不应为空", depts.isEmpty());
            Assert.assertEquals("应查询到 4 个部门", 4, depts.size());

            long withEmps = depts.stream()
                    .filter(d -> d.getEmps() != null && !d.getEmps().isEmpty())
                    .count();
            Assert.assertTrue("应存在包含员工的部门", withEmps > 0);

            System.out.println("一对多查询：共 " + depts.size() + " 个部门，其中 " + withEmps + " 个有下属员工");
            depts.forEach(System.out::println);
        }
    }

    /** 多对一：嵌套结果映射，一条 JOIN SQL 装配员工与其部门 */
    @Test
    public void testSelectEmpWithDept() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            List<Emp> emps = session.getMapper(EmpMapper.class).selectEmpWithDept();

            Assert.assertFalse("员工列表不应为空", emps.isEmpty());

            long withDept = 0;
            long withoutDept = 0;
            for (Emp e : emps) {
                if (e.getDeptId() == null) {
                    // 没有部门的员工，LEFT JOIN 后关联对象为 null，属正常情况
                    Assert.assertNull("无部门的员工 deptInfo 应为 null", e.getDeptInfo());
                    withoutDept++;
                } else {
                    Assert.assertNotNull(e.getEmpName() + " 的 deptInfo 不应为空", e.getDeptInfo());
                    Assert.assertNotNull(e.getEmpName() + " 的部门名不应为空", e.getDeptInfo().getDeptName());
                    withDept++;
                }
            }
            Assert.assertTrue("应存在关联到部门的员工", withDept > 0);

            System.out.println("多对一查询（嵌套结果）：共 " + emps.size() + " 名员工，"
                    + withDept + " 名装配了部门信息，" + withoutDept + " 名无部门（如测试插入的临时数据）");
            emps.forEach(System.out::println);
        }
    }

    /** 多对一：分步查询 + 延迟加载 */
    @Test
    public void testSelectEmpWithDeptStep() {
        try (SqlSession session = MyBatisUtils.openSession()) {
            List<Emp> emps = session.getMapper(EmpMapper.class).selectEmpWithDeptStep();

            Assert.assertFalse("员工列表不应为空", emps.isEmpty());
            System.out.println(">>> 已取到员工列表（此时若为懒加载，第二条部门 SQL 尚未发出）");

            // 访问 deptInfo 时才会触发部门查询
            for (Emp e : emps) {
                if (e.getDeptId() != null) {
                    Assert.assertNotNull(e.getEmpName() + " 的 deptInfo 不应为空", e.getDeptInfo());
                    System.out.println("   " + e.getEmpName() + " -> " + e.getDeptInfo().getDeptName());
                }
            }
        }
    }
}