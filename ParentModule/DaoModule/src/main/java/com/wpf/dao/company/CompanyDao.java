package com.wpf.dao.company;

import com.wpf.domain.company.Company;

import java.util.List;

/**
 * 创建时间：2020/11/1
 * SassExport项目-Dao层接口
 * @author wpf
 */

public interface CompanyDao {

    /**
     * 查询Company表的所有数据
     * @return Company的List列表
     */
    List<Company> queryAllCompanies();

    /**
     * 根据Id值获取Company表中的某一条数据
     * @param companyId company的Id值
     * @return Company
     */
    Company queryOneCompanyById(String companyId);

    /**
     * 向数据库的Company表中插入一条数据
     * @param company 需要插入的数据封装的Company对象
     * @return 影响的行数
     */
    Integer insertOneCompany(Company company);

    /**
     * 根据company数据的主键来修改其中的一条数据
     * @param company 需要修改的数据
     * @return 影响的行数
     */
    Integer updateOneCompanyById(Company company);
}
