package com.wpf.service.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Module;
import com.wpf.domain.system.Role;

import java.util.List;

/**
 * 创建时间：2020/11/6
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface RoleService {

    /**
     * 结束PageHelper插件来获取Role
     * @param pageSize 页面显示的记录条数
     * @param currentPageNum 目前所在的页面页数
     * @param companyId 需要查询的部门所属的公司Id
     * @return role的分页数据
     */
    PageInfo<Role> findRoleByPage(Integer pageSize, Integer currentPageNum, String companyId);

    /**
     * 根据一个role的Id值来获取对应数据
     * @param id 需要查询的role的Id值
     * @return 查找到的role对象
     */
    Role findRoleById(String id);

    /**
     * 添加一条role的数据记录
     * @param role 需要添加的对象
     * @return 成功与否
     */
    Boolean addOneRole(Role role);

    /**
     * 修改一条role的数据记录
     * @param role 修改的记录
     * @return 成功与否
     */
    Boolean changeOneRole(Role role);

    /**
     * 删除一条role的数据记录
     * @param roleId 需要删除的role的Id值
     * @return 成功与否
     */
    Boolean removeOneRole(String roleId);

    /**
     * 更新角色对应的权限
     * @param moduleIds 更新后选择的权限
     * @param roleId 需要更新权限的角色Id值
     */
    void updateModulesByRoleId(String moduleIds, String roleId);

    /**
     * 通过用户的Id值来查找用户对应的角色
     * @param userId 用户的Id
     * @return 角色集合
     */
    List<Role> findRolesByUserId(String userId);

    /**
     * 查询出某个公司的所有角色，以供用户选择
     * @param companyId 查询的公司Id
     * @return 所有角色列表
     */
    List<Role> findAllRolesByCompanyId(String companyId);
}
