package com.wpf.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.wpf.domain.cargo.*;
import com.wpf.domain.system.User;
import com.wpf.service.cargo.ContractService;
import com.wpf.service.cargo.ExportProductService;
import com.wpf.service.cargo.ExportService;
import com.wpf.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 创建时间：2020/11/16
 * SassExport项目-Web层-Export控制器
 * @author wpf
 */
@Controller
@RequestMapping("/cargo/export")
public class ExportController extends BaseController {

    @Reference
    ContractService contractService;
    @Reference
    ExportService exportService;
    @Reference
    ExportProductService exportProductService;

    /**
     * 分页查询已提交的Contract数据
     * @param pageSize 一页显示的数据数量
     * @param pageNum 前页码数
     * @return page
     */
    @RequestMapping("/contractList")
    public String surfAllSubmitContract(Model model,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id
        String companyId = getCompanyId();

        //将查询条件设置为降序排序
        ContractExample example = new ContractExample();
        example.setOrderByClause("create_time desc");
        //设置查询条件为companyId和state
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        criteria.andStateEqualTo(1);

        PageInfo<Contract> pageInfo = contractService.findByPage(example, pageNum, pageSize);

        model.addAttribute("pageInfo", pageInfo);
        return "/cargo/export/export-contractList";
    }

    @RequestMapping("/list")
    public String surfAllExport(Model model,
                        @RequestParam(defaultValue = "5") Integer pageSize,
                        @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id
        String companyId = getCompanyId();

        //将查询条件设置为降序排序
        ExportExample example = new ExportExample();
        example.setOrderByClause("create_time desc");
        //设置查询条件为companyId和state
        ExportExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);

        PageInfo<Export> pageInfo = exportService.findByPage(example, pageNum, pageSize);

        model.addAttribute("pageInfo", pageInfo);

        return "cargo/export/export-list";
    }

    /**
     * 跳转到确认报运的页面
     * @param contractIds 需要报运的合同字符串
     * @return
     */
    @RequestMapping("toExport")
    public String jumpToExportPage(String contractIds) {
        request.setAttribute("contractIds", contractIds);
        return "cargo/export/export-toExport";
    }

    @RequestMapping("/edit")
    public String submitToExport(Export export) {
        //设置合同的企业信息
        export.setCompanyId(getCompanyId());
        export.setCompanyName(getCompanyName());

        User user = getLoginUser();
        if (StringUtils.isEmpty(export.getId())) {
            //设置操作用户的相关信息
            export.setCreateBy(user.getId());
            export.setCreateDept(user.getDeptId());
            exportService.save(export);
        } else {
            export.setUpdateBy(user.getId());
            exportService.update(export);
        }
        return "redirect:/cargo/export/list";
    }

    /**
     * 跳转到添加contract的页面，回显数据
     * @param id 需要修改的合同的Id值
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //查询到需要回显的数据
        //查询报运单数据
        Export export = exportService.findById(id);
        //查询报运商品数据
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);

        model.addAttribute("export", export);
        model.addAttribute("exportProductList", exportProductList);

        return "/cargo/export/export-update";
    }

}
