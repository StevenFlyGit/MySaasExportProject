package com.wpf.web.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 创建时间：2020/11/1
 * 统一异常处理类
 * @author wpf
 */
@Component
public class CustomException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errorObj", e);
        modelAndView.setViewName("error/500error");
        e.printStackTrace();
        return modelAndView;
    }
}
