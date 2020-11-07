package com.wpf.service.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Role;

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

}
