package com.wpf.dao.system;

import com.wpf.domain.system.Department;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 在pe_dept中根据companyId来查询数据，并除去某一条数据
     * @param id 需要除去的department的Id值
     * @param companyId 查询用到的companyId到的值
     * @return 符合条件的department的list集合
     */
    List<Department> queryDepartmentExceptIdByCompanyId(@Param("id") String id, @Param("companyId") String companyId);

    /**
     * 向pe_dept表中添加一条数据
     * @param department 需要添加的数据
     * @return 影响的行数
     */
    Integer insertOneDepartment(Department department);

    /**
     * 修改pe_dept表中的一条数据
     * @param department 需要修改的数据
     * @return 影响的行数
     */
    Integer updateOneDepartment(Department department);

    /**
     * 删除pe_dept表中的一条数据
     * @param deptId 需要删除的数据的Id值
     * @return 影响的行数
     */
    Integer deleteOneDepartment(String deptId);

    /**
     * 查找某一个部门的子部门列表
     * @param parentDeptId 需要查找的上级部门的Id
     * @return 查找到的部门List集合
     */
    List<Department> queryDepartmentByParentDeptId(String parentDeptId);
}
