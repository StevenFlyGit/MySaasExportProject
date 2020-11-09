package com.wpf.web.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 创建时间：2020/11/9
 * 自定义凭证匹配器
 * @author wpf
 */

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //获取用户输入的用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //getCredentials方法内部调用的是getPassword方法
        String inputPwd = new String((char[]) upToken.getCredentials());
        //getPrincipal方法内部调用的是getUsername方法
        String username = (String) upToken.getPrincipal();

        //对用户输入的密码加盐加密
        String encodePwd = new Md5Hash(inputPwd, username).toString();

        //获取数据库中的正确密码
        String dbPwd = (String) info.getCredentials();

        return encodePwd.equals(dbPwd);
    }
}
