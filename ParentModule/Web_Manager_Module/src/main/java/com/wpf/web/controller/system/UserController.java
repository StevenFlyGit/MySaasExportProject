package com.wpf.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.wpf.dao.system.DepartmentDao;
import com.wpf.domain.system.Department;
import com.wpf.domain.system.User;
import com.wpf.service.system.UserService;
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
import java.util.UUID;

/**
 * 创建时间：2020/11/6
 * SassExport项目-Web层-User控制器
 * @author wpf
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentDao departmentDao;

    @RequestMapping("/list")
    public String surfAllUser(Model model,
                                    @RequestParam(defaultValue = "5") Integer pageSize,
                                    @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        PageInfo<User> pageInfo = userService.findUserByPage(pageSize, pageNum, companyId);
        model.addAttribute("pageInfo", pageInfo);
        return "system/user/user-list";
    }

    /**
     * 跳转到添加user的页面，回显数据
     * 将所有的user对象(除了其本身)显示到添加页面中，用于选择上级部门
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        //查询到需要回显的数据
        User user = userService.findUserById(id);
        //查询到下拉列表的数据
        List<Department> list = departmentDao.queryDepartmentByCompanyId(companyId);
        model.addAttribute("user", user);
        model.addAttribute("list", list);
        return "system/user/user-update";
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
        return "system/user/user-add";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param user 需要添加或修改的user对象
     * @return page
     */
    @RequestMapping("/edit")
    public String editUser(User user) {

        //需要获取当用户所属的公司Id和公司名，目前先写死
        user.setCompanyId(getCompanyId());
        user.setCompanyName(getCompanyName());
        if (StringUtils.isEmpty(user.getId())) {
            userService.addOneUser(user);
        } else {
            userService.changeOneUser(user);
        }
        return "redirect:/system/user/list";
    }

    /**
     * 删除数据的控制器方法
     * @param id 需要删除的数据的Id值
     * @return json数据
     */
    @RequestMapping("/delete")
    @ResponseBody()
    public Map<String, Boolean> removeUser(String id) {
        Map<String, Boolean> map = new HashMap<>();
        Boolean result = userService.removeOneUser(id);
        if (result) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

}
