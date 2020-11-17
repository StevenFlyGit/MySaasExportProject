package com.wpf.dao.statistic;

import java.util.List;
import java.util.Map;

/**
 * 创建时间：2020/11/17
 * SassExport项目-Dao层接口
 * 查询关于统计报表的数据
 * @author wpf
 */

public interface StatisticDao {

    List<Map<String, Object>> queryFactorySale();

    List<Map<String, Object>> queryProductTopSale(Integer topNum);

    List<Map<String, Object>> queryOnlinePopulation();
}
