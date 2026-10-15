package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.Emp;

import java.util.List;

/**
 * 员工 Mapper 接口。
 * 继承 BaseMapper&lt;Emp&gt; 获得内置 CRUD（insert/selectById/updateById/deleteById/selectList 等），
 * 自定义动态 SQL 方法继续走 EmpMapper.xml。
 */
public interface EmpMapper extends BaseMapper<Emp> {

    /** 查询所有员工 */
    List<Emp> selectAll();

    /** 多条件模糊查询员工 */
    List<Emp> selectByCondition(Emp emp);

    /** 分支查询：优先按姓名精确查，其次按部门查，否则查全部 */
    List<Emp> selectByChoose(Emp emp);

    /** 关键字检索：姓名/部门/岗位 模糊匹配 */
    List<Emp> selectByKeyword(@Param("keyword") String keyword);

    /** 批量新增员工 */
    int insertBatch(@Param("emps") List<Emp> emps);

    /**
     * 多对一查询：查询员工及其所属部门（嵌套结果映射，用 &lt;association&gt; 组装）。
     */
    List<Emp> selectEmpWithDept();

    /**
     * 多对一查询（分步查询）：先查员工列表，访问 deptInfo 时再按 dept_id 查部门。
     * 演示 MyBatis 的延迟加载（懒加载）机制。
     */
    List<Emp> selectEmpWithDeptStep();
}