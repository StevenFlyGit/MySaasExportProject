package com.wpf.dao.system;

import com.wpf.domain.system.Module;
import com.wpf.domain.system.Role;

import java.util.List;

/**
 * 创建时间：2020/11/7
 * SassExport项目-Dao层接口
 * 处理ss_module表的数据
 * @author wpf
 */

public interface ModuleDao {

    /**
     * 根据部门Id在表中查询到相应的数据
     * @param deptId 需要查询的部门的Id值
     * @return 查询到的Module对象
     */
    Module queryModuleById(String deptId);

    /**
     * 查询所有的权限信息
     * @return 查询到的Module对象的List集合
     */
    List<Module> queryAllModules();

    /**
     * 向ss_module表中添加一条数据
     * @param module 需要添加的数据
     * @return 影响的行数
     */
    Integer insertOneModule(Module module);

    /**
     * 修改ss_module表中的一条数据
     * @param module 需要修改的数据
     * @return 影响的行数
     */
    Integer updateOneModule(Module module);

    /**
     * 删除ss_module表中的一条数据
     * @param deptId 需要删除的数据的Id值
     * @return 影响的行数
     */
    Integer deleteOneModule(String deptId);

    /**
     * 连表查询-根据角色Id查询对应的权限
     * @param roleId 需要查询权限的角色Id值
     * @return 查询到的结果集合
     */
    List<Module> queryModuleByRoleId(String roleId);

    /**
     * 根据ss_module表中的belong字段来查询module
     * @param moduleBelong belong字段的值
     * @return 查询到的结果集合
     */
    List<Module> queryModuleByBelong(int moduleBelong);

    /**
     * 连表查询-连接表pe_role_user、pe_role_module、ss_module来查询用户对应的权限
     * @param userId 需要查询的用户Id值
     * @return 查询到的结果集合
     */
    List<Module> queryModuleByUserId(String userId);
}
