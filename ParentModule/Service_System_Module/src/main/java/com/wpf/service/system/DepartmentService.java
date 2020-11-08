package com.wpf.service.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Department;

import java.util.List;

/**
 * 创建时间：2020/11/4
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface DepartmentService {

    /**
     * 结束PageHelper插件来获取dept
     * @param pageSize 页面显示的记录条数
     * @param currentPageNum 目前所在的页面页数
     * @param companyId 需要查询的部门所属的公司Id
     * @return department的分页数据
     */
    PageInfo<Department> findDepartmentByPage(Integer pageSize, Integer currentPageNum, String companyId);

    /**
     * 根据一个department的Id值来获取对应数据
     * @param id 需要查询的department的Id值
     * @return 查找到的department对象
     */
    Department findDepartmentById(String id);

    /**
     * 查询所有的department数据，除了其本身
     * @param id 除去的department对象
     * @return 符合条件的department的数据
     */
    List<Department> findDepartmentsExceptIdByCompanyId(String id, String companyId);

    /**
     * 添加一条department的数据记录
     * @param department 需要添加的对象
     * @return 成功与否
     */
    Boolean addOneDepartment(Department department);

    /**
     * 根据公司的id查找出所有的部门信息
     * @return 部门的List集合
     */
    List<Department> findDepartmentByCompanyId(String companyId);

    /**
     * 修改一条department的数据记录
     * @param department 修改的记录
     * @return 成功与否
     */
    Boolean changeOneDepartment(Department department);

    /**
     * 删除一条department的数据记录
     * @param deptId 需要删除的department的Id值
     * @return 成功与否
     */
    Boolean removeOneDepartment(String deptId);
}
