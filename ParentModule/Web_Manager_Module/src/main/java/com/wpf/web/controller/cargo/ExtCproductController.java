package com.wpf.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.wpf.domain.cargo.*;
import com.wpf.domain.system.User;
import com.wpf.service.cargo.ExtCproductService;
import com.wpf.service.cargo.FactoryService;
import com.wpf.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 创建时间：2020/11/14
 * SassExport项目-Web层-ExtContractProduct控制器
 * @author wpf
 */
@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    ExtCproductService extCproductService;
    @Reference
    FactoryService factoryService;

    /**
     * 分页查询ExtCproductService数据
     * @param pageSize 一页显示的数据数量
     * @param pageNum 当前页码数
     * @return page
     */
    @RequestMapping("/list")
    public String surfAllExtCproduct(Model model, String contractProductId, String contractId,
                                  @RequestParam(defaultValue = "5") Integer pageSize,
                                  @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id
        String companyId = getCompanyId();

        ExtCproductExample example = new ExtCproductExample();
        //设置查询条件为companyId
        ExtCproductExample.Criteria criteria = example.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);

        PageInfo<ExtCproduct> pageInfo = extCproductService.findByPage(example, pageNum, pageSize);
        //查询下拉列表的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        model.addAttribute("factoryList", factoryList);
        model.addAttribute("contractProductId", contractProductId);
        model.addAttribute("contractId", contractId);
        model.addAttribute("pageInfo", pageInfo);
        return "/cargo/extc/extc-list";
    }

    /**
     * 跳转到添加extProduct的页面，回显数据
     * 将所有的factory数据显示到修改页面中，用于选择生产厂家
     * @param id 需要修改的附件的Id值
     * @return
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //查询到需要回显的数据
        ExtCproduct extCproduct = extCproductService.findById(id);
        //查询下拉列表的厂家数据
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(example);

        model.addAttribute("extCproduct", extCproduct);
        model.addAttribute("factoryList", factoryList);

        return "/cargo/extc/extc-update";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param extCproduct 需要添加或修改的extCproduct对象
     * @return page
     */
    @RequestMapping("/edit")
    public String editExtCproduct(ExtCproduct extCproduct) {
        //设置附件的企业信息
        extCproduct.setCompanyId(getCompanyId());
        extCproduct.setCompanyName(getCompanyName());

        if (StringUtils.isEmpty(extCproduct.getId())) {
            //设置操作用户的相关信息
//            User user = getLoginUser();
//            extCproduct.setCreateBy(user.getId());
//            extCproduct.setCreateDept(user.getDeptId());
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list?contractId=" + extCproduct.getContractId()
                + "&contractProductId=" + extCproduct.getContractProductId();
    }

    /**
     * 删除数据的控制器方法
     * @param id 需要删除的数据的Id值
     * @return page
     */
    @RequestMapping("/delete")
    public String removeExtCproduct(String id, String contractId, String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/contractProduct/list?contractId=" + contractId
                + "&contractProductId=" + contractProductId;
    }



}
