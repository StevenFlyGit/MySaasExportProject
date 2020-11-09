package com.wpf.dao.system;

import com.wpf.domain.system.Role;
import com.wpf.domain.system.SysLog;

import java.util.List;

/**
 * 创建时间：2020/11/8
 * SassExport项目-Dao层接口
 * 处理st_sys_log表的数据
 * @author wpf
 */

public interface SysLogDao {

    /**
     * 根据companyId来查询部门表中的相应信息
     * @param companyId 需要查询的companyId的值
     * @return 查询到的SysLog对象的List集合
     */
    List<SysLog> queryRoleByCompanyId(String companyId);

    /**
     * 向st_sys_log表中插入一条数据
     * @param log 需要插入的数据
     */
    void insertLog(SysLog log);
}
