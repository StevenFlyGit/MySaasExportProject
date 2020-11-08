package com.wpf.service.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Module;
import com.wpf.domain.system.Role;

import java.util.List;

/**
 * 创建时间：2020/11/7
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface ModuleService {

    /**
     * 结束PageHelper插件来获取Module
     * @param pageSize 页面显示的记录条数
     * @param currentPageNum 目前所在的页面页数
     * @param companyId 需要查询的部门所属的公司Id
     * @return role的分页数据
     */
    PageInfo<Module> findModuleByPage(Integer pageSize, Integer currentPageNum, String companyId);

    /**
     * 根据一个module的Id值来获取对应数据
     * @param id 需要查询的role的Id值
     * @return 查找到的role对象
     */
    Module findModuleById(String id);


    /**
     * 添加一条module的数据记录
     * @param module 需要添加的对象
     * @return 成功与否
     */
    Boolean addOneModule(Module module);

    /**
     * 修改一条module的数据记录
     * @param module 修改的记录
     * @return 成功与否
     */
    Boolean changeOneModule(Module module);

    /**
     * 删除一条module的数据记录
     * @param moduleId 需要删除的module的Id值
     * @return 成功与否
     */
    Boolean removeOneModule(String moduleId);

    /**
     * 查询表中所有的数据
     * @return 查询到的结果集合
     */
    List<Module> findAllModules();

    /**
     *
     * 根据角色的Id值来查询其对应的权限
     * @param roleId 需要查询的角色Id
     * @return 查询到的结果集合
     */
    List<Module> findModulesByRoleId(String roleId);

    /** 查询某个用户的所有权限
     * @param userId 需要查询的用户Id
     * @return 查询到的所有权限集合
     */
    List<Module> findModulesByUserId(String userId);
}
