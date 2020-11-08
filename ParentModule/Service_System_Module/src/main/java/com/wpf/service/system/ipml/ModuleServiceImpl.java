package com.wpf.service.system.ipml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.ModuleDao;
import com.wpf.dao.system.UserDao;
import com.wpf.domain.system.Module;
import com.wpf.domain.system.Role;
import com.wpf.domain.system.User;
import com.wpf.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 创建时间：2020/11/7
 * SassExport项目-Service层实现类
 * 处理权限数据的相关业务
 * @author wpf
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;
    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo<Module> findModuleByPage(Integer pageSize, Integer currentPageNum, String companyId) {
        //启用PageHelper插件
        PageHelper.startPage(currentPageNum, pageSize);
        //调用Dao层根据companyId来查询Role
        List<Module> roleList = moduleDao.queryAllModules();
        //将Role的数据封装到pageInfo对象中
        PageInfo<Module> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    @Override
    public List<Module> findAllModules() {
        return moduleDao.queryAllModules();
    }

    @Override
    public Module findModuleById(String id) {
        return moduleDao.queryModuleById(id);
    }

    @Override
    public Boolean addOneModule(Module module) {
        module.setId(UUID.randomUUID().toString());

        Integer row = moduleDao.insertOneModule(module);
        return row > 0;
    }

    @Override
    public Boolean changeOneModule(Module module) {

        Integer row = moduleDao.updateOneModule(module);
        return row > 0;
    }

    @Override
    public Boolean removeOneModule(String moduleId) {
        Integer row = moduleDao.deleteOneModule(moduleId);
        return  row > 0;
    }

    @Override
    public List<Module> findModulesByRoleId(String roleId) {
        return moduleDao.queryModuleByRoleId(roleId);
    }

    @Override
    public List<Module> findModulesByUserId(String userId) {
        //根据Id查用户
        User user = userDao.queryUserById(userId);
        int userDegree = user.getDegree();
        //判断用户的身份
        if (userDegree == 0) {
            //Degree为0代表示saas管理员，仅可访问saas模块
            return moduleDao.queryModuleByBelong(0);
        } else if (userDegree == 1) {
            //Degree为0代表示企业管理员，可访问除saas模块以外的全部模块
            return moduleDao.queryModuleByBelong(1);
        } else {
            //Degree为0代表示企业其他用户，根据用户权限动态查询权限(同样不能范根saas模块)
            return moduleDao.queryModuleByUserId(userId);
        }
    }

}
