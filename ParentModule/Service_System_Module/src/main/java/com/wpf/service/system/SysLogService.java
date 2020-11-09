package com.wpf.service.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.SysLog;

/**
 * 创建时间：2020/11/8
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface SysLogService {

    /**
     * 结束PageHelper插件来获取SysLog
     * @param pageSize 页面显示的记录条数
     * @param currentPageNum 目前所在的页面页数
     * @param companyId 需要查询的部门所属的公司Id
     * @return role的分页数据
     */
    PageInfo<SysLog> findRoleByPage(Integer pageSize, Integer currentPageNum, String companyId);

    /**
     * 存储日志信息的业务方法
     * @param log 日志对象
     */
    void saveLog(SysLog log);
}
