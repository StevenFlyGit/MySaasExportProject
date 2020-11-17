package com.wpf.service.statistic.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wpf.dao.statistic.StatisticDao;
import com.wpf.service.statistic.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 创建时间：2020/11/17
 * SassExport项目-Service层实现类
 * @author wpf
 */
@Service(timeout = 5000000)
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    StatisticDao statisticDao;

    @Override
    public List<Map<String, Object>> findFactorySale() {
        return statisticDao.queryFactorySale();
    }

    @Override
    public List<Map<String, Object>> findProductTopSale(Integer topNum) {
        return statisticDao.queryProductTopSale(topNum);
    }

    @Override
    public List<Map<String, Object>> findOnlinePopulation() {
        return statisticDao.queryOnlinePopulation();
    }


}
