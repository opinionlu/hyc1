package org.example;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Emp;
import org.example.mapper.EmpMapper;
import org.example.util.MyBatisUtils;

import java.util.List;

/**
 * 第一个 MyBatis 程序：查询所有员工。
 * SqlSessionFactory 的构建已封装到 MyBatisUtils 工具类（static 块），避免重复创建。
 */
public class MyBatisDemo {

    public static void main(String[] args) {
        // 1. 通过工具类获取 SqlSessionFactory（全局只构建一次，见 MyBatisUtils）
        // 2. 开启 SqlSession（try-with-resources，用完自动关闭）
        try (SqlSession session = MyBatisUtils.openSession()) {
            // 3. 获取 Mapper 动态代理对象
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            // 4. 调用查询，打印结果
            List<Emp> list = mapper.selectAll();
            list.forEach(System.out::println);
        }
    }
}
