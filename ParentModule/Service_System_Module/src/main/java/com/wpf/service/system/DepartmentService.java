package com.wpf.service.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Department;

/**
 * 创建时间：2020/11/4
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface DepartmentService {

    /**
     * 结束PageHelper插件来获取dept
     * @param pageSize 页面显示的记录条数
     * @param currentPageNum 目前所在的页面页数
     * @param companyId 需要查询的部门所属的公司Id
     * @return department的分页数据
     */
    PageInfo<Department> findDeptByPage(Integer pageSize, Integer currentPageNum, String companyId);
}
