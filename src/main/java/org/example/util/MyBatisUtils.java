package org.example.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * MyBatis-Plus 工具类：加载全局配置文件并构建 SqlSessionFactory。
 * 使用 MP 的 MybatisSqlSessionFactoryBuilder，并注册分页插件。
 */
public class MyBatisUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        // 读取类路径下的全局配置文件（try-with-resources 确保流被关闭）
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
            sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(inputStream);

            // 注册分页插件：MybatisPlusInterceptor + PaginationInnerInterceptor
            MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
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
