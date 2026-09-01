package org.example.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis 工具类：负责加载全局配置文件并构建 SqlSessionFactory。
 */
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        // 读取类路径下的全局配置文件（try-with-resources 确保流被关闭）
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("加载 mybatis-config.xml 失败", e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    /**
     * 打开一个 SqlSession（默认不自动提交，写操作需手动 commit）。
     */
    public static SqlSession openSession() {
        return sqlSessionFactory.openSession();
    }
}
