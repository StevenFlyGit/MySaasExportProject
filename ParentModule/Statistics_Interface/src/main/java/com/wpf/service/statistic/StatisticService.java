package com.wpf.service.statistic;

import java.util.List;
import java.util.Map;

/**
 * 创建时间：2020/11/17
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface StatisticService {
    List<Map<String, Object>> findFactorySale();

    List<Map<String, Object>> findProductTopSale(Integer topNum);

    List<Map<String, Object>> findOnlinePopulation();
}
