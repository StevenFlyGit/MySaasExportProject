package com.wpf.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建时间：2020/11/18
 * 测试quartz定时执行的方法
 * @author wpf
 */

public class testTask {

    /**
     * 测试每5秒执行一次的方法
     */
    public void testEvery5Second() {
        System.out.println("每隔5秒执行一次，" + "当前时间：" +
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }


}
