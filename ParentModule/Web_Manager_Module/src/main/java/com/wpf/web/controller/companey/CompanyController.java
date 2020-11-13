package com.wpf.web.controller.companey;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wpf.domain.company.Company;

import lombok.extern.slf4j.Slf4j;
import com.wpf.service.company.CompanyService;
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

    @Reference
    private CompanyService companyService;

    @RequestMapping("/list")
    public String surfController(Model model) {
        model.addAttribute("CompanyList", companyService.findAllCompanies());
        return "company/company-list";
    }

    @RequestMapping("/addDate")
    public String surfController(Date date) {
        System.out.println(date);
        return "success";
    }

    /**
     * 修改时回显数据
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToAddPage(String id, Model model) {
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
    public String edit(Company company) {
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
    public String jumpToAddPage() {
        return "company/company-add";
    }

    /**
     * 执行删除操作的控制器方法
     * @param id 需要删除的company的Id值
     * @return
     */
    @RequestMapping("/delete")
    public String removeCompany(String id) {
        companyService.removeOneCompany(id);
        return "redirect:/company/list";
    }

}
