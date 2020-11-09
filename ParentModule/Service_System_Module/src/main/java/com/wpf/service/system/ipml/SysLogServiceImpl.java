package com.wpf.service.system.ipml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.ModuleDao;
import com.wpf.dao.system.SysLogDao;
import com.wpf.domain.system.Role;
import com.wpf.domain.system.SysLog;
import com.wpf.service.system.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 创建时间：2020/11/8
 * SassExport项目-Service层实现类
 * 处理日志的相关业务
 * @author wpf
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public PageInfo<SysLog> findRoleByPage(Integer pageSize, Integer currentPageNum, String companyId) {
        //启用PageHelper插件
        PageHelper.startPage(currentPageNum, pageSize);
        //调用Dao层根据companyId来查询Role
        List<SysLog> logList = sysLogDao.queryRoleByCompanyId(companyId);
        //将Role的数据封装到pageInfo对象中
        PageInfo<SysLog> pageInfo = new PageInfo<>(logList);
        return pageInfo;
    }

    @Override
    public void saveLog(SysLog log) {
        log.setId(UUID.randomUUID().toString());
        sysLogDao.insertLog(log);
    }

}
