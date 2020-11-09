package com.wpf.web.controller;

import com.wpf.domain.system.Module;
import com.wpf.domain.system.User;
import com.wpf.service.system.ModuleService;
import com.wpf.service.system.UserService;
import com.wpf.web.exception.ManyUserException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 创建时间：2020/11/3
 * 处理起始页面的跳转请求
 * @author wpf
 */
@Controller
public class LoginController extends BaseController{

    @Autowired
    UserService userService;
    @Autowired
    ModuleService moduleService;

    /**
     * 用户登录校验：index.jsp -> main.jsp
     * @param email 用户输入登录email
     * @param password 用户输入登录password
     * @return url
     */
    //@RequestMapping("/login")
    public String loginJumpToMain(String email, String password) {
        if (email == null || password == null) {
        } else if (email.equals("") || password.equals("")){
            request.setAttribute("errorMsg", "请输入密码和用户名");
        } else {
            Map<String, Object> loginResult = userService.userLogin(email, password);
            if ((Boolean) loginResult.get("result")) {
                User user = (User) loginResult.get("user");
                //将用户数据放入session域中
                session.setAttribute("LoginUser", user);

                //查找用户的权限集合，用于显示菜单列表
                List<Module> moduleList = moduleService.findModulesByUserId(user.getId());
                session.setAttribute("moduleList", moduleList);

                return "home/main";
            } else {
                request.setAttribute("errorMsg", loginResult.get("errorMsg"));
            }
        }
        return "forward:/login.jsp";
    }

    @RequestMapping("/login")
    public String loginByShiro(String email, String password) {
        if (email == null || password == null) {
            return "forward:/login.jsp";
        } else if (email.equals("") || password.equals("")){
            request.setAttribute("errorMsg", "请输入密码和用户名");
            return "forward:/login.jsp";
        } else {
            try {
                //获得shiro中的subject对象，表示当前用户
                Subject subject = SecurityUtils.getSubject();
                //创建AuthenticationToken对象，将用户名和密码信息封装进去，用于认证校验
                AuthenticationToken token = new UsernamePasswordToken(email, password);

            /*
            !!!用户认证的核心方法，执行该方法后，会提交到shiro的安全管理器，即DefaultWebSecurityManager类，
            之后再提交到认证器，再执行自定义realm类中的AuthenticationInfo方法，即自定义的认证业务逻辑
            */
                subject.login(token);

                //运行到此，证明已认证通过
                //获取登录用户的对象
                User user = (User) subject.getPrincipal();
                session.setAttribute("LoginUser", user);

                //查找用户的权限集合，用于显示菜单列表
                List<Module> moduleList = moduleService.findModulesByUserId(user.getId());
                session.setAttribute("moduleList", moduleList);

                return "home/main";
            } catch (AuthenticationException e) {
                if (e instanceof UnknownAccountException) {
                    request.setAttribute("errorMsg", "邮箱地址不存在");
                } else if (e instanceof IncorrectCredentialsException) {
                    request.setAttribute("errorMsg", "密码错误");
                } else if (e instanceof ManyUserException) {
                    request.setAttribute("errorMsg", e.getMessage());
                }
                e.printStackTrace();
                return "forward:/login.jsp";
            } catch (RuntimeException e) {
                request.setAttribute("errorMsg", e.getMessage());
                return "forward:/login.jsp";
            }
        }
    }

    /**
     * main.jsp页面内，跳转到home页面
     * @return url
     */
    @RequestMapping("/home")
    public String inMainJumpToHome() {
        return "home/home";
    }


    @RequestMapping("/logout")
    public String userLogout() {
        //清楚shiro中的用户认证信息
        //SecurityUtils.getSubject().logout();
        //清楚session域中的信息
        session.removeAttribute("LoginUser");
        session.invalidate();
        return "forward:/login.jsp";
    }


}
