package org.example.mapper;

import org.example.entity.Emp;

import java.util.List;

/**
 * 员工 Mapper 接口，对应映射文件 EmpMapper.xml。
 */
public interface EmpMapper {

    /** 根据主键查询员工 */
    Emp selectById(Integer empId);

    /** 查询所有员工 */
    List<Emp> selectAll();

    /** 新增员工，主键自增回填到 emp.empId */
    int insert(Emp emp);

    /** 更新员工 */
    int update(Emp emp);

    /** 根据主键删除员工 */
    int deleteById(Integer empId);
}
