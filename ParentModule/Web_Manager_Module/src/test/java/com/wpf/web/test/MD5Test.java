package com.wpf.web.test;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * 创建时间：2020/11/9
 * MD5算法测试类
 * @author wpf
 */

public class MD5Test {

    @Test
    public void md5() {
        Md5Hash md5Hash = new Md5Hash("1");
        System.out.println(md5Hash.toString());
    }

    @Test
    public void md5WithSalt() {
        Md5Hash md5Hash = new Md5Hash("123", "test@itheima.com");
        System.out.println(md5Hash.toString());
    }

    @Test
    public void md5WithSaltAndIteration() {
        Md5Hash md5Hash = new Md5Hash("1", "lw@export.com", 2);
        System.out.println(md5Hash.toString());
    }


}
