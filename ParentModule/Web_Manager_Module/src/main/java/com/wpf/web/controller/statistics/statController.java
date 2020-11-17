package com.wpf.web.controller.statistics;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wpf.service.statistic.StatisticService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 创建时间：2020/11/17
 * SassExport项目-Web层-statistic控制器
 *  * 用于处理输出文件的相关请求
 * @author wpf
 */
@Controller
@RequestMapping("/stat")
public class statController {

    @Reference
    StatisticService statisticService;

    @RequestMapping("/toCharts")
    public String jumpToStat(@RequestParam("chartsType") String path) {
        return "stat/stat-" + path;
    }

    @RequestMapping("/factoryCharts")
    @ResponseBody
    public List<Map<String, Object>> surfFactorySale() {
        return statisticService.findFactorySale();
    }

    @RequestMapping("/sellCharts")
    @ResponseBody
    public List<Map<String, Object>> surfProductTopSale(
            @RequestParam(defaultValue = "5") int topNum) {
        return statisticService.findProductTopSale(topNum);
    }

    @RequestMapping("/onlineCharts")
    @ResponseBody
    public List<Map<String, Object>> surfOnlinePopulation() {
        return statisticService.findOnlinePopulation();
    }

}
