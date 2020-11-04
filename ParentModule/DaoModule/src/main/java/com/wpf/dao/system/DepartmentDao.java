package com.wpf.dao.system;

import com.wpf.domain.system.Department;

import java.util.List;

/**
 * 创建时间：2020/11/4
 * SassExport项目-Dao层接口
 * 处理pe_dept表的数据
 * @author wpf
 */

public interface DepartmentDao {
    /**
     * 根据companyId来查询部门表中的相应信息
     * @param companyId 需要查询的companyId的值
     * @return 查询到的Department对象的List集合
     */
    List<Department> queryDepartmentByCompanyId(String companyId);

    /**
     * 根据部门Id在表中查询到相应的数据
     * @param deptId 需要查询的部门的Id值
     * @return 查询到的Department对象
     */
    Department queryDepartmentById(String deptId);
}
