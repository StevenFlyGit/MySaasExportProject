package com.wpf.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Department;
import com.wpf.service.system.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建时间：2020/11/4
 * SassExport项目-Web层-Department控制器
 * @author wpf
 */
@Controller
@RequestMapping("/system/dept")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/list")
    public String surfAllDepartment(Model model,
                    @RequestParam(defaultValue = "5") Integer pageSize,
                    @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = "1";
        PageInfo<Department> pageInfo = departmentService.findDeptByPage(pageSize, pageNum, companyId);
        model.addAttribute("pageInfo", pageInfo);
        return "system/dept/dept-list";
    }

}
