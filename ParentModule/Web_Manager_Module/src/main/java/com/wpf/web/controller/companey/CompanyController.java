package com.wpf.web.controller.companey;

import com.wpf.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

}
