package com.wpf.service.company;

import com.wpf.domain.company.Company;

import java.util.List;

/**
 * 创建时间：2020/11/1
 * SassExport项目-Service层接口
 * @author wpf
 */

public interface CompanyService {

    List<Company> findAllCompanies();
}
