package com.wpf.web.controller;

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
        //目前先写死，完成登录功能后从用户数据中获取
        return "1";
    }

    public String getCompanyName() {
        //目前先写死，完成登录功能后从用户数据中获取
        return "传智播客教育股份有限公司";
    }
}
