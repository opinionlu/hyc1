package org.example;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.session.SqlSession;
import org.example.entity.Emp;
import org.example.mapper.EmpMapper;
import org.example.util.MyBatisUtils;

import java.util.List;

/**
 * 条件构造器 + 分页查询演示。
 * 运行：mvn -q compile exec:java -Dexec.mainClass=org.example.WrapperPageDemo
 */
public class WrapperPageDemo {

    public static void main(String[] args) {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            // ===== 1. 条件构造器多条件查询（LambdaQueryWrapper，类型安全） =====
            LambdaQueryWrapper<Emp> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(Emp::getEmpName, "张")   // 姓名模糊
                    .eq(Emp::getStatus, 1)        // 在职
                    .ge(Emp::getSalary, 8000)     // 薪资 >= 8000
                    .orderByDesc(Emp::getSalary); // 按薪资降序
            List<Emp> list = mapper.selectList(wrapper);
            System.out.println("【条件查询】姓名含[张] 且 在职 且 薪资>=8000，共 " + list.size() + " 条：");
            list.forEach(System.out::println);

            // ===== 2. 分页查询（Page + selectPage） =====
            Page<Emp> page = new Page<>(1, 3); // 第 1 页，每页 3 条
            Page<Emp> result = mapper.selectPage(page, null);
            System.out.println("\n【分页】第 " + result.getCurrent() + " 页 / 共 " + result.getPages()
                    + " 页 / 总记录 " + result.getTotal() + " 条（每页 " + result.getSize() + " 条）：");
            result.getRecords().forEach(System.out::println);
        }
    }
}
