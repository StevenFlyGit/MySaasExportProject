package com.wpf.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.domain.system.Department;
import com.wpf.domain.system.Role;
import com.wpf.service.system.RoleService;
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
 * 创建时间：2020/11/6
 * SassExport项目-Web层-Role控制器
 * @author wpf
 */
@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping("/list")
    public String surfAllRole(Model model,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        PageInfo<Role> pageInfo = roleService.findRoleByPage(pageSize, pageNum, companyId);
        model.addAttribute("pageInfo", pageInfo);
        return "system/role/role-list";
    }

    /**
     * 跳转到添加role的页面，回显数据
     * 将所有的role对象(除了其本身)显示到添加页面中，用于选择上级部门
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        //查询到需要回显的数据
        Role role = roleService.findRoleById(id);
        //查询到下拉列表的数据
        List<Department> list = departmentDao.queryDepartmentByCompanyId(companyId);
        model.addAttribute("role", role);
        model.addAttribute("list", list);
        return "system/role/role-update";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/toAdd")
    public String jumpToAddPage(Model model) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        //根据公司Id查找所有的部门，用于下拉框展示上级部门的选项
        List<Department> list = departmentDao.queryDepartmentByCompanyId(companyId);
        model.addAttribute("list", list);
        return "system/role/role-add";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param role 需要添加或修改的role对象
     * @return page
     */
    @RequestMapping("/edit")
    public String editRole(Role role) {

        //需要获取当用户所属的公司Id和公司名，目前先写死
        role.setCompanyId(getCompanyId());
        role.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(role.getId())) {
            roleService.addOneRole(role);
        } else {
            roleService.changeOneRole(role);
        }
        return "redirect:/system/role/list";
    }

    /**
     * 删除数据的控制器方法
     * @param id 需要删除的数据的Id值
     * @return json数据
     */
    @RequestMapping("/delete")
//    @ResponseBody()
//    public Map<String, Boolean> removeRole(String id) {
//        Map<String, Boolean> map = new HashMap<>();
//        Boolean result = roleService.removeOneRole(id);
//        if (result) {
//            map.put("success", true);
//        } else {
//            map.put("success", false);
//        }
//        return map;
//    }
    public String removeRole(String id) {
        roleService.removeOneRole(id);
        return "redirect:/system/role/list";
    }


}
