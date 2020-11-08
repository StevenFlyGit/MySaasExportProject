package com.wpf.web.controller;

import com.wpf.domain.system.User;
import com.wpf.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

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

    /**
     * 用户登录校验：index.jsp -> main.jsp
     * @param emailURL 用户输入登录email
     * @param password 用户输入登录password
     * @return url
     */
    @RequestMapping("/login")
    public String loginJumpToMain(String emailURL, String password) {
        if (emailURL == null || password == null) {
        } else if (emailURL.equals("") || password.equals("")){
            request.setAttribute("errorMsg", "请输入密码和用户名");
        } else {
            Map<String, Object> loginResult = userService.userLogin(emailURL, password);
            if ((Boolean) loginResult.get("result")) {
                session.setAttribute("LoginUser", loginResult.get("user"));
                return "home/main";
            } else {
                request.setAttribute("errorMsg", loginResult.get("errorMsg"));
            }
        }
        return "forward:/login.jsp";
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
        session.removeAttribute("LoginUser");
        session.invalidate();
        return "forward:/login.jsp";
    }


}
