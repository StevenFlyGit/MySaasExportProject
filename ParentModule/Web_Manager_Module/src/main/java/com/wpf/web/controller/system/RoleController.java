package com.wpf.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.domain.system.Department;
import com.wpf.domain.system.Module;
import com.wpf.domain.system.Role;
import com.wpf.service.system.DepartmentService;
import com.wpf.service.system.ModuleService;
import com.wpf.service.system.RoleService;
import com.wpf.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
    private DepartmentService departmentService;
    @Autowired
    private ModuleService moduleService;

    /**
     * 分页查询Role数据
     * @param pageSize 一页显示的数据数量
     * @param pageNum 当前页码数
     * @return page
     */
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
     * @param id 需要修改的角色的Id值
     * @return
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //查询到需要回显的数据
        Role role = roleService.findRoleById(id);
        model.addAttribute("role", role);
        return "system/role/role-update";
    }

    /**
     * 查询下拉列表并跳转到添加页面
     * @return
     */
    @RequestMapping("/toAdd")
    public String jumpToAddPage(Model model) {
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
     *
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

    /**
     * 通过Id查找角色并将查找到的对象返回到显示角色对应权限的页面
     * @param roleId 需要查找的角色的Id
     * @return page
     */
    @RequestMapping("/roleModule")
    public String roleModule(String roleId) {
        Role role = roleService.findRoleById(roleId);
        request.setAttribute("role", role);
        return "/system/role/role-module";
    }

    /**
     * 回显某一个角色所拥有的权限数据
     * @param roleId 需要查询权限的角色Id值
     * @return json
     */
    @RequestMapping("/getModuleNodes")
    @ResponseBody
    public List<Map<String, Object>> getModulesByRoleId(String roleId) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        //查询出所有的权限
        List<Module> allModules = moduleService.findAllModules();
        //查询出当前角色所拥有的权限
        List<Module> selectedModule = moduleService.findModulesByRoleId(roleId);
        //遍历所有权限，找出角色拥有的，并存入Map中
        for (Module module : allModules) {
            //4.1 创建map，封装权限信息（map的key是固定的: id、pId、name、checked、open）
            Map<String, Object> map = new HashMap<>();
            map.put("id", module.getId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());
            if (selectedModule.contains(module)) {
                map.put("checked", true);
            }
            map.put("open", true);
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 根据用户的选择，更改某一个角色的权限
     * @param moduleIds 修改后选择的权限的Id值数组
     * @param roleId 需要修改的角色的Id值
     * @return
     */
    @RequestMapping("/updateRoleModule")
    public String updateModulesOfRole(String moduleIds, String roleId){
        roleService.updateModulesByRoleId(moduleIds, roleId);
        return "redirect:/system/role/list";
    }
}