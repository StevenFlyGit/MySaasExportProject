package com.wpf.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.wpf.domain.system.Role;
import com.wpf.domain.system.SysLog;
import com.wpf.service.system.ModuleService;
import com.wpf.service.system.SysLogService;
import com.wpf.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 创建时间：2020/11/8
 * SassExport项目-Web层-Role控制器
 * @author wpf
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 分页查询SysLog数据
     * @param pageSize 一页显示的数据数量
     * @param pageNum 当前页码数
     * @return page
     */
    @RequestMapping("/list")
    public String surfAllLog(Model model,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id，目前先写死
        String companyId = getCompanyId();
        PageInfo<SysLog> pageInfo = sysLogService.findRoleByPage(pageSize, pageNum, companyId);
        model.addAttribute("pageInfo", pageInfo);
        return "system/log/log-list";
    }

}
