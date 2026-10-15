package org.example;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.util.MyBatisUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * MyBatis 集成冒烟测试：验证全局配置与 db.properties 能正确加载。
 * 具体的 CRUD 功能测试见 org.example.mapper.EmpMapperTest。
 */
public class MyBatisTest {

    /** 验证 SqlSessionFactory 能成功构建（mybatis-config.xml 与 db.properties 配置正确） */
    @Test
    public void testBuildSqlSessionFactory() {
        SqlSessionFactory factory = MyBatisUtils.getSqlSessionFactory();
        Assert.assertNotNull("SqlSessionFactory 构建失败", factory);
        System.out.println("SqlSessionFactory 构建成功");
    }
}