package com.wpf.provider;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 创建时间：2020/11/13
 * 提供数据统计业务服务的入口方法
 * @author wpf
 */

public class StatisticProvider {

    @Test
    public void provideCargo() throws IOException {
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        System.in.read();
    }

}
