package com.wpf.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Department;
import com.wpf.service.system.DepartmentService;
import com.wpf.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建时间：2020/11/4
 * SassExport项目-Web层-Department控制器
 * @author wpf
 */
@Controller
@RequestMapping("/system/dept")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/list")
    public String surfAllDepartment(Model model,
                    @RequestParam(defaultValue = "5") Integer pageSize,
                    @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        PageInfo<Department> pageInfo = departmentService.findDeptByPage(pageSize, pageNum, companyId);
        model.addAttribute("pageInfo", pageInfo);
        return "system/dept/dept-list";
    }

    /**
     * 跳转到添加department的页面，回显数据
     * 将所有的department对象(除了其本身)显示到添加页面中，用于选择上级部门
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        //查询到需要回显的数据
        Department department = departmentService.findDeptById(id);
        //查询到下拉列表的数据
        List<Department> departmentList = departmentService.findDeptsExceptIdByCompanyId(id, companyId);
        model.addAttribute("department", department);
        model.addAttribute("departmentList", departmentList);
        return "system/dept/dept-update";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String jumpToAddPage(Model model) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = "1";
        //根据公司Id查找所有的部门，用于下拉框展示上级部门的选项
        List<Department> departmentList = departmentService.findDeptByCompanyId(companyId);
        model.addAttribute("departmentList", departmentList);
        return "system/dept/dept-add";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param department 需要添加或修改的department对象
     * @return page
     */
    @RequestMapping("/edit")
    public String editDepartment(Department department) {
        //需要获取当用户所属的公司Id和公司名，目前先写死
        department.setCompanyId(getCompanyId());
        department.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(department.getDeptId())) {
            departmentService.addOneDepartment(department);
        } else {
            departmentService.changeOneDepartment(department);
        }
        return "redirect:/system/dept/list";
    }

    /**
     * 删除数据的控制器方法
     * @param id 需要删除的数据的Id值
     * @return json数据
     */
    @RequestMapping("/delete")
    @ResponseBody()
    public Map<String, Integer> removeDepartment(String id) {
        Map<String, Integer> map = new HashMap<>();
        Boolean result = departmentService.removeOneDepartment(id);
        if (result) {
            map.put("message", 1);
        } else {
            map.put("message", 0);
        }
        return map;
    }

}