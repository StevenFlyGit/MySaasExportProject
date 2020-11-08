package com.wpf.service.system.ipml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.domain.system.Department;
import com.wpf.service.system.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public PageInfo<Department> findDepartmentByPage(Integer pageSize, Integer currentPageNum, String companyId) {
        //启用PageHelper插件
        PageHelper.startPage(currentPageNum, pageSize);
        //调用Dao层根据companyId来查询Department
        List<Department> departmentList = departmentDao.queryDepartmentByCompanyId(companyId);
        //将Department的数据封装到pageInfo对象中
        PageInfo<Department> pageInfo = new PageInfo<>(departmentList);
        return pageInfo;
    }

    @Override
    public Department findDepartmentById(String id) {
        return departmentDao.queryDepartmentById(id);
    }

    @Override
    public List<Department> findDepartmentsExceptIdByCompanyId(String id, String companyId) {
        return departmentDao.queryDepartmentExceptIdByCompanyId(id, companyId);
    }

    @Override
    public Boolean addOneDepartment(Department department) {
        //解决用户选择上级部门时可能未选择的问题(也可在映射文件中解决)
        department.setDeptId(UUID.randomUUID().toString());
//        if (department.getParentDept() != null && "".equals(department.getParentDept().getDeptId())) {
//            department.getParentDept().setDeptId(null);
//        }
        Integer row = departmentDao.insertOneDepartment(department);
        return row > 0;
    }

    @Override
    public List<Department> findDepartmentByCompanyId(String companyId) {
        return departmentDao.queryDepartmentByCompanyId(companyId);
    }

    @Override
    public Boolean changeOneDepartment(Department department) {
        //解决用户选择上级部门时可能未选择的问题
        if (department.getParentDept() != null && "".equals(department.getParentDept().getDeptId())) {
            department.getParentDept().setDeptId(null);
        }
        Integer row = departmentDao.updateOneDepartment(department);
        return row > 0;
    }

    @Override
    public Boolean removeOneDepartment(String deptId) {
        //查找该部门是否有子部门
        List<Department> departmentList = departmentDao.queryDepartmentByParentDeptId(deptId);

        if (departmentList == null || departmentList.size() == 0) {
            //没有子部门则可以删除
            departmentDao.deleteOneDepartment(deptId);
            return true;
        }
        return false;
    }

}
