package org.example;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.Emp;
import org.example.mapper.EmpMapper;
import org.example.util.MyBatisUtils;

import java.util.List;
import java.util.Scanner;
public class SearchDemo {

    public static void main(String[] args) {
        try (SqlSession session = MyBatisUtils.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n请输入查询关键字（姓名/部门/岗位，输入 exit 退出）：");
                String keyword = scanner.nextLine().trim();

                if ("exit".equalsIgnoreCase(keyword)) {
                    System.out.println("已退出。");
                    break;
                }
                if (keyword.isEmpty()) {
                    System.out.println("关键字不能为空，请重新输入。");
                    continue;
                }

                List<Emp> list = mapper.selectByKeyword(keyword);
                System.out.println("关键字[" + keyword + "] 匹配到 " + list.size() + " 条：");
                list.forEach(System.out::println);
            }
        }
    }
}
