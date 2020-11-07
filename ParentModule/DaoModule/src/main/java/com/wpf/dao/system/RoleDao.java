package com.wpf.dao.system;

import com.wpf.domain.company.Company;
import com.wpf.domain.system.Role;

import java.util.List;

/**
 * 创建时间：2020/11/6
 * SassExport项目-Dao层接口
 * 处理pe_role表的数据
 * @author wpf
 */

public interface RoleDao {

    /**
     * 根据companyId来查询部门表中的相应信息
     * @param companyId 需要查询的companyId的值
     * @return 查询到的Role对象的List集合
     */
    List<Role> queryRoleByCompanyId(String companyId);

    /**
     * 根据部门Id在表中查询到相应的数据
     * @param deptId 需要查询的部门的Id值
     * @return 查询到的Role对象
     */
    Role queryRoleById(String deptId);

    /**
     * 向pe_role表中添加一条数据
     * @param role 需要添加的数据
     * @return 影响的行数
     */
    Integer insertOneRole(Role role);

    /**
     * 修改pe_role表中的一条数据
     * @param role 需要修改的数据
     * @return 影响的行数
     */
    Integer updateOneRole(Role role);

    /**
     * 删除pe_role表中的一条数据
     * @param deptId 需要删除的数据的Id值
     * @return 影响的行数
     */
    Integer deleteOneRole(String deptId);

}
