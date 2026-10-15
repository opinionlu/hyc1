-- ============================================================
--  ssm_emp 数据库完整初始化脚本
--  包含：部门表 dept、员工表 emp（含 dept_id 外键）、初始数据
--  说明：脚本会重建两张表，执行后数据恢复为初始 4 条
-- ============================================================

USE ssm_emp;

-- ------------------------------------------------------------
-- 1. 清理旧表（先删 emp，再删 dept，避免外键约束报错）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS emp;
DROP TABLE IF EXISTS dept;

-- ------------------------------------------------------------
-- 2. 部门表 dept
-- ------------------------------------------------------------
CREATE TABLE dept (
    dept_id    INT PRIMARY KEY AUTO_INCREMENT COMMENT '部门编号',
    dept_name  VARCHAR(50) NOT NULL COMMENT '部门名称',
    location   VARCHAR(50) COMMENT '办公地点',
    manager    VARCHAR(50) COMMENT '部门负责人'
) COMMENT '部门表';

INSERT INTO dept (dept_name, location, manager) VALUES
('研发部', 'A座3楼', '张伟'),
('市场部', 'A座5楼', '王强'),
('人事部', 'B座2楼', '赵敏'),
('财务部', 'B座1楼', '孙丽');

-- ------------------------------------------------------------
-- 3. 员工表 emp（新增 dept_id 外键列；保留原 dept 名称列以兼容已有查询）
-- ------------------------------------------------------------
CREATE TABLE emp (
    emp_id     INT PRIMARY KEY AUTO_INCREMENT COMMENT '员工编号',
    emp_name   VARCHAR(50)  NOT NULL COMMENT '姓名',
    gender     CHAR(1)      DEFAULT '男' COMMENT '性别',
    dept       VARCHAR(50)  COMMENT '部门名称',
    dept_id    INT          COMMENT '部门编号（外键 -> dept.dept_id）',
    post       VARCHAR(50)  COMMENT '岗位',
    salary     DECIMAL(10,2) COMMENT '薪资',
    hire_date  DATE         COMMENT '入职时间',
    status     TINYINT      DEFAULT 1 COMMENT '状态：1在职 0离职',
    CONSTRAINT fk_emp_dept FOREIGN KEY (dept_id) REFERENCES dept (dept_id)
) COMMENT '员工表';

INSERT INTO emp (emp_name, gender, dept, dept_id, post, salary, hire_date, status) VALUES
('张伟', '男', '研发部', 1, 'Java工程师', 12000.00, '2024-07-01', 1),
('李娜', '女', '研发部', 1, '前端工程师', 10000.00, '2024-08-15', 1),
('王强', '男', '市场部', 2, '市场专员',    8000.00, '2023-03-10', 1),
('赵敏', '女', '人事部', 3, '人事专员',    7500.00, '2022-06-20', 0);

-- ------------------------------------------------------------
-- 4. 校验：查看两表数据与外键关联情况
-- ------------------------------------------------------------
SELECT e.emp_id, e.emp_name, e.dept AS dept_name, e.dept_id, d.dept_name AS joined_name, d.location, d.manager
FROM emp e
LEFT JOIN dept d ON e.dept_id = d.dept_id
ORDER BY e.emp_id;