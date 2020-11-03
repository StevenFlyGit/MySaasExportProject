package com.wpf.service.company;

import com.wpf.domain.company.Company;

import java.util.List;

/**
 * 创建时间：2020/11/1
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface CompanyService {

    /**
     * 获取数据库中的所有Company数据
     * @return Company的List列表
     */
    List<Company> findAllCompanies();

    /**
     * 根据Company的Id值来获取某一条Company数据
     * @param companyId 需要获取的Company的Id值
     * @return 查询到的Company数据
     */
    Company findOneCompanyById(String companyId);

    /**
     * 添加一条company的数据
     * @param company 需要添加的Company对象
     * @return 是否成功
     */
    Boolean saveOneCompany(Company company);

    /**
     * 修改一条company的数据
     * @param company
     * @return
     */
    Boolean changeOneCompany(Company company);
}
