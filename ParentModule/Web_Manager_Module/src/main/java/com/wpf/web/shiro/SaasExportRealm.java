package com.wpf.web.shiro;

import com.wpf.domain.system.Module;
import com.wpf.domain.system.User;
import com.wpf.service.system.ModuleService;
import com.wpf.service.system.UserService;
import com.wpf.web.exception.ManyUserException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 创建时间：2020/11/9
 * 自定义的Realm类
 * 用于shrio调用执行认证和授权的方法
 * @author wpf
 */

public class SaasExportRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;

    /**
     * 认证方法
     * @param token 该接口使用时需要转换为对应的实现类UsernamePasswordToken
     *        该对象包含用户登录时输入的密码、账号信息
     * @return 认证对象，包含从数据库查询到的用户对象和密码信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //将接口转换为实现类
        UsernamePasswordToken upToke = (UsernamePasswordToken) token;
        //获取用户输入的用户名(即email)
        String username = upToke.getUsername();
        //调用service层方法查询对应的用户对象
        List<User> userList = userService.findUserByEmail(username);

        if (userList.size() == 0) {
            return null;
        } else if (userList.size() > 1){
            throw new ManyUserException("用户邮箱地址重复，请联系管理员");
        } else {
            //获得数据库中的用户密码
            String dbPwd = userList.get(0).getPassword();

            //创建返回值对象
            // 参数1：用户的身份，通过subject.getPrincipal()获取的就是这里的参数1
            // 参数2：数据库中正确的密码告诉shiro。shiro会自动校验（用户输入的密码、数据库中的密码）
            // 参数3：realm名称; getName() 获取默认名称。
            SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(userList.get(0),dbPwd,getName());
            return sai;
        }
    }

    /**
     * 授权方法
     * @param principals 其中包含了登录后的用户对象
     * @return 包含授权后的权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取登录的用户对象
        User user = (User) principals.getPrimaryPrincipal();
        //根据用户Id查询对应的权限
        List<Module> moduleList = moduleService.findModulesByUserId(user.getId());
        //遍历权限集合，将对应的权限加入到shiro框架中
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        if (moduleList != null && moduleList.size() > 0) {
            moduleList.forEach(module->{
                sai.addStringPermission(module.getName());
            });
        }

        return sai;
    }
}
