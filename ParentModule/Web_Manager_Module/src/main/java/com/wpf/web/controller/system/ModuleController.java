package com.wpf.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.domain.system.Department;
import com.wpf.domain.system.Module;
import com.wpf.service.system.ModuleService;
import com.wpf.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 创建时间：2020/11/7
 * SassExport项目-Web层-Module控制器
 * @author wpf
 */
@Controller
@RequestMapping("system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 分页查询Module数据
     * @param pageSize 一页显示的数据数量
     * @param pageNum 当前页码数
     * @return page
     */
    @RequestMapping("/list")
    public String surfAllModule(Model model,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        PageInfo<Module> pageInfo = moduleService.findModuleByPage(pageSize, pageNum, companyId);
        model.addAttribute("pageInfo", pageInfo);
        return "system/module/module-list";
    }

    /**
     * 跳转到添加module的页面，回显数据
     * 将所有的module对象(除了其本身)显示到添加页面中，用于选择上级部门
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //查询到需要回显的数据
        Module module = moduleService.findModuleById(id);
        //查询到下拉列表的数据
        List<Module> list = moduleService.findAllModules();
        model.addAttribute("module", module);
        model.addAttribute("list", list);
        return "system/module/module-update";
    }

    /**
     * 查询下拉列表并跳转到添加页面
     * @return psge
     */
    @RequestMapping("/toAdd")
    public String jumpToAddPage(Model model) {
        //查询到下拉列表的数据
        List<Module> list = moduleService.findAllModules();
        model.addAttribute("list", list);
        return "system/module/module-add";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param module 需要添加或修改的module对象
     * @return page
     */
    @RequestMapping("/edit")
    public String editModule(Module module) {
        if (StringUtils.isEmpty(module.getId())) {
            moduleService.addOneModule(module);
        } else {
            moduleService.changeOneModule(module);
        }
        return "redirect:/system/module/list";
    }

    /**
     * 删除数据的控制器方法
     * @param id 需要删除的数据的Id值
     * @return json数据
     */
    @RequestMapping("/delete")
    public String removeModule(String id) {
        moduleService.removeOneModule(id);
        return "redirect:/system/module/list";
    }


}
