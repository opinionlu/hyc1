package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entity.Dept;

import java.util.List;

/**
 * 部门 Mapper 接口。
 * 继承 BaseMapper&lt;Dept&gt; 获得内置 CRUD；多表查询方法走 DeptMapper.xml。
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 一对多查询：查询所有部门及其下属员工（嵌套结果映射，用 &lt;collection&gt; 聚合）。
     */
    List<Dept> selectDeptWithEmps();

    /**
     * 供分步查询使用：按主键查询单个部门。
     * 该方法是 EmpMapper 中 &lt;association select="..."&gt; 的目标语句。
     */
    Dept selectDeptById(Integer deptId);
}