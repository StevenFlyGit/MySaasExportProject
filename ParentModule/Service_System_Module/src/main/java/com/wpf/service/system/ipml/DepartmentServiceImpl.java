package com.wpf.service.system.ipml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.domain.system.Department;
import com.wpf.service.system.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建时间：2020/11/4
 * SassExport项目-Service层实现类
 * 处理部门数据的相关业务
 * @author wpf
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public PageInfo<Department> findDeptByPage(Integer pageSize, Integer currentPageNum, String companyId) {
        //启用PageHelper插件
        PageHelper.startPage(currentPageNum, pageSize);
        //调用Dao层根据companyId来查询Department
        List<Department> departmentList = departmentDao.queryDepartmentByCompanyId(companyId);
        //将Department的数据封装到pageInfo对象中
        PageInfo<Department> pageInfo = new PageInfo<>(departmentList);
        return pageInfo;
    }
}
