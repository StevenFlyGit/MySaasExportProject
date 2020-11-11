package com.wpf.service.company.impl;

import com.wpf.dao.company.CompanyDao;
import com.wpf.domain.company.Company;
import com.wpf.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;
import java.util.UUID;

/**
 * 创建时间：2020/11/1
 * SassExport项目-Service层实现类
 * @author wpf
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public List<Company> findAllCompanies() {
        return companyDao.queryAllCompanies();
    }

    @Override
    public Company findOneCompanyById(String companyId) {
        return companyDao.queryOneCompanyById(companyId);
    }

    @Override
    public Boolean saveOneCompany(Company company) {
        //通过工具类生成随机Id
        company.setId(UUID.randomUUID().toString());
        Integer row = companyDao.insertOneCompany(company);
        return row > 0;
    }

    @Override
    public Boolean changeOneCompany(Company company) {
        Integer row = companyDao.updateOneCompanyById(company);
        return row > 0;
    }

    @Override
    public Boolean removeOneCompany(String id) {
        Integer row = companyDao.deleteCompany(id);
        return row > 0;
    }
}
