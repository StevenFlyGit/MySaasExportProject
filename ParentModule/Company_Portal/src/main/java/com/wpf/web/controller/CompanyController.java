package com.wpf.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wpf.domain.company.Company;
import com.wpf.service.company.CompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建时间：2020/11/10
 * Dubbo部署企业功能
 * @author wpf
 */

@RestController
public class CompanyController {

    @Reference
    private CompanyService companyService;

    @RequestMapping("/apply")
    public String companyApply(Company company) {
        try {
            companyService.saveOneCompany(company);
            return "1";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

}
