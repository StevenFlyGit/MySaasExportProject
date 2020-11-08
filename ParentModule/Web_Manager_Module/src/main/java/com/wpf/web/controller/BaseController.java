package com.wpf.web.controller;

import com.wpf.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 创建时间：2020/11/6
 * 从所有控制器中抽取出一部分共有的属性和方法
 * @author wpf
 */
@Controller
public abstract class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    public String getCompanyId() {
        return getLoginUser().getCompanyId();
    }

    public String getCompanyName() {
        return getLoginUser().getCompanyName();
    }

    public User getLoginUser() {
        return (User) session.getAttribute("LoginUser");
    }
}
