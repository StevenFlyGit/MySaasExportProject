package com.wpf.dao.system;

import com.wpf.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创建时间：2020/11/6
 * SassExport项目-Dao层接口
 * 处理pe_user表的数据
 * @author wpf
 */

public interface UserDao {

    /**
     * 根据companyId来查询部门表中的相应信息
     * @param companyId 需要查询的companyId的值
     * @return 查询到的User对象的List集合
     */
    List<User> queryUserByCompanyId(String companyId);

    /**
     * 根据部门Id在表中查询到相应的数据
     * @param deptId 需要查询的部门的Id值
     * @return 查询到的User对象
     */
    User queryUserById(String deptId);

    /**
     * 向pe_user表中添加一条数据
     * @param user 需要添加的数据
     * @return 影响的行数
     */
    Integer insertOneUser(User user);

    /**
     * 修改pe_user表中的一条数据
     * @param user 需要修改的数据
     * @return 影响的行数
     */
    Integer updateOneUser(User user);

    /**
     * 删除pe_user表中的一条数据
     * @param deptId 需要删除的数据的Id值
     * @return 影响的行数
     */
    Integer deleteOneUser(String deptId);

    /**
     * 查询一个用户所绑定的角色数量
     * @param userId 需要查询的用户的Id
     * @return 数量值
     */
    Integer queryRoleByUserId(String userId);
}
