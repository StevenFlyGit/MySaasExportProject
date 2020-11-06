package com.wpf.service.system.ipml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.dao.system.UserDao;
import com.wpf.domain.system.User;
import com.wpf.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


/**
 * 创建时间：2020/11/6
 * SassExport项目-Service层实现类
 * 处理用户数据的相关业务
 * @author wpf
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public PageInfo<User> findUserByPage(Integer pageSize, Integer currentPageNum, String companyId) {
        //启用PageHelper插件
        PageHelper.startPage(currentPageNum, pageSize);
        //调用Dao层根据companyId来查询User
        List<User> userList = userDao.queryUserByCompanyId(companyId);
        //将User的数据封装到pageInfo对象中
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public User findUserById(String id) {
        return userDao.queryUserById(id);
    }

    @Override
    public Boolean addOneUser(User user) {
        user.setId(UUID.randomUUID().toString());
        //解决用户选择部门时可能未选择的问题
        if ("".equals(user.getDeptId())) {
            user.setDeptId(null);
        }
        /*else {
            //通过部门Id来查找部门名称
            Department department = departmentDao.queryDepartmentById(user.getDeptId());
            user.setDeptName(department.getDeptName());
        }*/
        Integer row = userDao.insertOneUser(user);
        return row > 0;
    }

    @Override
    public Boolean changeOneUser(User user) {
        //解决用户选择部门时可能未选择的问题
        if ("".equals(user.getDeptId())) {
            user.setDeptId(null);
        }
        Integer row = userDao.updateOneUser(user);
        return row > 0;
    }

    @Override
    public Boolean removeOneUser(String userId) {
        //查找该用户是否绑定了角色
        Integer count = userDao.queryRoleByUserId(userId);

        if (count == 0) {
            //没有子部门则可以删除
            userDao.deleteOneUser(userId);
            return true;
        }
        return false;
    }


}
