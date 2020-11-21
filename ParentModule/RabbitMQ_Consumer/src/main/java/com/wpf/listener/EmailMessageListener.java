package com.wpf.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wpf.util.MailUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

/**
 * 创建时间：2020/11/20
 * 队列消息监听器
 * @author wpf
 */

public class EmailMessageListener implements MessageListener {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        try {
            JsonNode jsonNode = MAPPER.readTree(message.getBody());
            String email = jsonNode.get("email").asText();
            String title = jsonNode.get("title").asText();
            String content = jsonNode.get("content").asText();
            System.out.println("发送邮箱：" + email);
            System.out.println("标题：" + title);
            System.out.println("内容：" + content);

            MailUtil.sendMsg(email, title, content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
