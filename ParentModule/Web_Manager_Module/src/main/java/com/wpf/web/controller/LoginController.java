package com.wpf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 创建时间：2020/11/3
 * 处理起始页面的跳转请求
 * @author wpf
 */
@Controller
public class LoginController {

    /**
     * index.jsp -> main.jsp
     * @param emailURL
     * @param password
     * @return url
     */
    @RequestMapping("/login")
    public String loginJumpToMain(String emailURL, String password) {
        return "home/main";
    }

    /**
     * main.jsp页面内，跳转到home页面
     * @return url
     */
    @RequestMapping("/home")
    public String inMainJumpToHome() {
        return "home/home";
    }



}
