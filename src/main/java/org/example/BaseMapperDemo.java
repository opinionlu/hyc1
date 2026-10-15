package org.example;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Emp;
import org.example.mapper.EmpMapper;
import org.example.util.MyBatisUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 零 SQL 通用 CRUD 演示：EmpMapper 继承 BaseMapper&lt;Emp&gt; 后，
 * 增删改查全部由 MyBatis-Plus 自动生成 SQL，无需写 XML 或 @Select 注解。
 * 运行：mvn -q compile exec:java -Dexec.mainClass=org.example.BaseMapperDemo
 */
public class BaseMapperDemo {

    public static void main(String[] args) {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            // ===== 增：insert（零 SQL，主键自增自动回填） =====
            Emp emp = new Emp();
            emp.setEmpName("零SQL演示");
            emp.setGender("男");
            emp.setDept("研发部");
            emp.setPost("测试");
            emp.setSalary(new BigDecimal("5000"));
            emp.setStatus(1);
            mapper.insert(emp);
            session.commit();
            System.out.println("【增】insert → 主键回填 empId = " + emp.getEmpId());

            // ===== 查：selectById / selectList / selectCount =====
            System.out.println("【查】selectById(" + emp.getEmpId() + ") → " + mapper.selectById(emp.getEmpId()));

            List<Emp> all = mapper.selectList(null);
            System.out.println("【查】selectList(null) 查全部 → " + all.size() + " 条");

            Long count = mapper.selectCount(null);
            System.out.println("【查】selectCount(null) 总记录数 → " + count + " 条");

            // ===== 改：updateById =====
            emp.setSalary(new BigDecimal("8888"));
            int upd = mapper.updateById(emp);
            session.commit();
            System.out.println("【改】updateById 影响 " + upd + " 条 → " + mapper.selectById(emp.getEmpId()));

            // ===== 删：deleteById =====
            int del = mapper.deleteById(emp.getEmpId());
            session.commit();
            System.out.println("【删】deleteById 影响 " + del + " 条");
        }
    }
}
