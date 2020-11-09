package com.wpf.web.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 创建时间：2020/11/10
 *
 * @author wpf
 */

public class ManyUserException extends AuthenticationException {
    public ManyUserException() {
        super();
    }

    public ManyUserException(String message) {
        super(message);
    }

}
