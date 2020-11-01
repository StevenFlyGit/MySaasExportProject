package com.wpf.service.company.impl;

import com.wpf.dao.company.CompanyDao;
import com.wpf.domain.company.Company;
import com.wpf.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
