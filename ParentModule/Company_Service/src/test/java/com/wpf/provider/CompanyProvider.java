package com.wpf.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * 创建时间：2020/11/10
 * 提供企业业务服务的入口方法
 * @author wpf
 */

public class CompanyProvider {

    @Test
    public void provideService() throws IOException {
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        System.in.read();
    }

}
