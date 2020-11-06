package com.wpf.service.system;

import com.wpf.domain.system.User;
import org.junit.Test;

import java.util.UUID;

/**
 * 创建时间：2020/11/6
 *
 * @author wpf
 */

public class UserServiceTest {

    @Test
    public void test1() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        System.out.println(user);
    }

}
