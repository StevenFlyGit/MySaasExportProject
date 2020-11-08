package com.wpf.service.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.User;

import java.util.List;

/**
 * 创建时间：2020/11/6
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface UserService {

    /**
     * 结束PageHelper插件来获取User
     * @param pageSize 页面显示的记录条数
     * @param currentPageNum 目前所在的页面页数
     * @param companyId 需要查询的部门所属的公司Id
     * @return user的分页数据
     */
    PageInfo<User> findUserByPage(Integer pageSize, Integer currentPageNum, String companyId);

    /**
     * 根据一个user的Id值来获取对应数据
     * @param id 需要查询的user的Id值
     * @return 查找到的user对象
     */
    User findUserById(String id);

    /**
     * 添加一条user的数据记录
     * @param user 需要添加的对象
     * @return 成功与否
     */
    Boolean addOneUser(User user);

    /**
     * 修改一条user的数据记录
     * @param user 修改的记录
     * @return 成功与否
     */
    Boolean changeOneUser(User user);

    /**
     * 删除一条user的数据记录
     * @param userId 需要删除的user的Id值
     * @return 成功与否
     */
    Boolean removeOneUser(String userId);

    /**
     * 删除用户原有的角色，保存现在选择的角色
     * @param userId 操作角色的用户的Id值
     * @param roleIds 需要保存的角色Id数组
     */
    void saveRoles(String userId, String[] roleIds);
}
