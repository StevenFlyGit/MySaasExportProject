package com.wpf.web.controller.companey;

import com.wpf.domain.company.Company;
import com.wpf.service.company.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 创建时间：2020/11/1
 * SassExport项目-Web层-Company控制器
 * @author wpf
 */
@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/list")
    private String surfController(Model model) {
        model.addAttribute("CompanyList", companyService.findAllCompanies());
        return "company/company-list";
    }

    @RequestMapping("/addDate")
    private String surfController(Date date) {
        System.out.println(date);
        return "success";
    }

    /**
     * 修改时回显数据
     * @return page
     */
    @RequestMapping("/toUpdate")
    private String jumpToAddPage(String id, Model model) {
        Company company = companyService.findOneCompanyById(id);
        model.addAttribute("company", company);
        return "company/company-update";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param company 需要添加或修改的company对象
     * @return page
     */
    @RequestMapping("/edit")
    private String edit(Company company) {
        if (StringUtils.isEmpty(company.getId())) {
            companyService.saveOneCompany(company);
        } else {
            companyService.changeOneCompany(company);
        }
        return "redirect:/company/list";
    }

    /**
     * 转发跳转到添加数据的页面
     * @return page
     */
    @RequestMapping("/toAdd")
    public String jumpAddPage() {
        return "company/company-add";
    }

}
