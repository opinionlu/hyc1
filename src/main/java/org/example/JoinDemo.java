package org.example;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Dept;
import org.example.entity.Emp;
import org.example.mapper.DeptMapper;
import org.example.mapper.EmpMapper;
import org.example.util.MyBatisUtils;

import java.util.List;

/**
 * 多表关联查询演示。
 * 运行：mvn -q compile exec:java -Dexec.mainClass=org.example.JoinDemo
 */
public class JoinDemo {

    public static void main(String[] args) {
        try (SqlSession session = MyBatisUtils.openSession()) {
            DeptMapper deptMapper = session.getMapper(DeptMapper.class);
            EmpMapper empMapper = session.getMapper(EmpMapper.class);

            // ===== 1. 一对多：部门 → 员工（<collection> 嵌套结果映射） =====
            System.out.println("========== 1. 一对多：部门 → 员工（<collection>） ==========");
            List<Dept> depts = deptMapper.selectDeptWithEmps();
            for (Dept d : depts) {
                System.out.println(d);
            }

            // ===== 2. 多对一：员工 → 部门（<association> 嵌套结果映射） =====
            System.out.println("\n========== 2. 多对一：员工 → 部门（<association> 嵌套结果） ==========");
            List<Emp> emps = empMapper.selectEmpWithDept();
            emps.forEach(System.out::println);

            // ===== 3. 多对一：分步查询 + 延迟加载 =====
            System.out.println("\n========== 3. 多对一：分步查询 + 延迟加载 ==========");
            List<Emp> emps2 = empMapper.selectEmpWithDeptStep();
            System.out.println("先取到员工列表（此刻若为懒加载，部门 SQL 尚未发出）");
            for (Emp e : emps2) {
                System.out.println("   " + e.getEmpName() + " 所属部门 = " + e.getDeptInfo());
            }
        }
    }
}