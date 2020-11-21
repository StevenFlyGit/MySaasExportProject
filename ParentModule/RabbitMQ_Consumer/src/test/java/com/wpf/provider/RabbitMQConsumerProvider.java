package com.wpf.provider;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.io.IOException;

/**
 * 创建时间：2020/11/20
 * 提供RabbitMQ的消费者服务
 * @author wpf
 */

public class RabbitMQConsumerProvider {

    @Test
    public void provideRabbitMQConsumer() throws IOException {
        ApplicationContext ac =
                new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*");
        System.in.read();
    }

}
