package com.wpf.service.system.ipml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.dao.system.ModuleDao;
import com.wpf.dao.system.RoleDao;
import com.wpf.dao.system.RoleDao;
import com.wpf.domain.system.Module;
import com.wpf.domain.system.Role;
import com.wpf.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

/**
 * 创建时间：2020/11/6
 * SassExport项目-Service层实现类
 * 处理角色数据的相关业务
 * @author wpf
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo<Role> findRoleByPage(Integer pageSize, Integer currentPageNum, String companyId) {
        //启用PageHelper插件
        PageHelper.startPage(currentPageNum, pageSize);
        //调用Dao层根据companyId来查询Role
        List<Role> roleList = roleDao.queryRoleByCompanyId(companyId);
        //将Role的数据封装到pageInfo对象中
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    @Override
    public Role findRoleById(String id) {
        return roleDao.queryRoleById(id);
    }

    @Override
    public Boolean addOneRole(Role role) {
        role.setId(UUID.randomUUID().toString());

        Integer row = roleDao.insertOneRole(role);
        return row > 0;
    }

    @Override
    public Boolean changeOneRole(Role role) {

        Integer row = roleDao.updateOneRole(role);
        return row > 0;
    }

    @Override
    public Boolean removeOneRole(String roleId) {
        //查找该角色是否被引用
        //查询被用户引用的次数
        //Integer userCount = roleDao.queryUserCountByRoleId(roleId);
        //查询被权限引用的次数
        //Integer moduleCount = roleDao.queryModuleCountByRoleId(roleId);
        int count = 0;
        if (count == 0) {
            //没有子部门则可以删除
            roleDao.deleteOneRole(roleId);
            return true;
        }
        return false;
    }

    @Override
    public void updateModulesByRoleId(String moduleIds, String roleId) {
        //先将角色对应的权限全部删除
        roleDao.deleteModulesOfRole(roleId);

        if (!StringUtils.isEmpty(moduleIds)) {
            String[] moduleStrings = moduleIds.split(",");
            if (moduleStrings != null && moduleStrings.length > 0) {
                for (int i = 0; i < moduleStrings.length; i++) {
                    roleDao.insertOneModuleByRoleId(roleId, moduleStrings[i]);
                }
            }
        }
    }

    @Override
    public List<Role> findRolesByUserId(String userId) {
        return roleDao.queryRoleByUserId(userId);
    }

    @Override
    public List<Role> findAllRolesByCompanyId(String companyId) {
        return roleDao.queryRoleByCompanyId(companyId);
    }


}
