package com.wpf.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.wpf.domain.cargo.Contract;
import com.wpf.domain.cargo.ContractExample;
import com.wpf.domain.system.User;
import com.wpf.service.cargo.ContractService;
import com.wpf.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 创建时间：2020/11/13
 * SassExport项目-Web层-Contract控制器
 * @author wpf
 */
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    ContractService contractService;

    /**
     * 分页查询Contract数据
     * @param pageSize 一页显示的数据数量
     * @param pageNum 当前页码数
     * @return page
     */
    @RequestMapping("/list")
    public String surfAllContract(Model model,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id
        String companyId = getCompanyId();

        //将查询条件设置为降序排序
        ContractExample example = new ContractExample();
        example.setOrderByClause("create_time desc");
        //设置查询条件为companyId(设置细粒度权限后可不用设置)
        ContractExample.Criteria criteria = example.createCriteria();
//        criteria.andCompanyIdEqualTo(companyId);
        PageInfo<Contract> pageInfo = null;
        //根据登录用户的degree属性级别，进行细粒度的权限控制，控制用户能查看的购销合同
        if (getLoginUser().getDegree() == 2) {
            //该用户可以查看到其所属部门及其子部门的所有合同数据、
            pageInfo = contractService.findByParentDept(getLoginUser().getDeptId(), pageNum, pageSize);
        } else if (getLoginUser().getDegree() == 3) {
            //该用户可以查看到其所属部门的合同数据
            criteria.andCreateDeptEqualTo(getLoginUser().getDeptId());
            pageInfo = contractService.findByPage(example, pageNum, pageSize);
        } else if (getLoginUser().getDegree() == 4) {
            //该用户仅可查看到其自己添加的合同数据
            criteria.andCreateByEqualTo(getLoginUser().getId());
            pageInfo = contractService.findByPage(example, pageNum, pageSize);
        }

        model.addAttribute("pageInfo", pageInfo);
        return "/cargo/contract/contract-list";
    }

    /**
     * 跳转到添加contract的页面，回显数据
     * @param id 需要修改的合同的Id值
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //查询到需要回显的数据
        Contract contract = contractService.findById(id);

        model.addAttribute("contract", contract);

        return "/cargo/contract/contract-update";
    }

    /**
     * 查询下拉列表并跳转到添加页面
     * @return page
     */
    @RequestMapping("/toAdd")
    public String jumpToAddPage() {
        return "/cargo/contract/contract-add";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param contract 需要添加或修改的contract对象
     * @return page
     */
    @RequestMapping("/edit")
    public String editContract(Contract contract) {
        //设置合同的企业信息
        contract.setCompanyId(getCompanyId());
        contract.setCompanyName(getCompanyName());

        User user = getLoginUser();
        if (StringUtils.isEmpty(contract.getId())) {
            //设置操作用户的相关信息
            contract.setCreateBy(user.getId());
            contract.setCreateDept(user.getDeptId());
            contractService.save(contract);
        } else {
            contract.setUpdateBy(user.getId());
            contractService.update(contract);
        }
        return "redirect:/cargo/contract/list";
    }

    /**
     * 删除数据的控制器方法
     * @param id 需要删除的数据的Id值
     * @return page
     */
    @RequestMapping("/delete")
    public String removeContract(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list";
    }

    /**
     * 查看方法，只可以浏览，不可以修改
     * @param id 需要查看的合同Id
     * @return page
     */
    @RequestMapping("toView")
    public String viewContract(Model model, String id) {
        Contract contract = contractService.findById(id);
        model.addAttribute("contract", contract);
        return "/cargo/contract/contract-view";
    }

    /**
     * 提交合同，将合同的state属性修改为1
     * @param id 需要提交的合同Id
     * @return page
     */
    @RequestMapping("/submit")
    public String submitContract(String id) {
        //创建一个合同对象，并将其state属性值设为1
        Contract contract = new Contract();
        contract.setState(1);
        //设置Id值
        contract.setId(id);
        //直接使用动态sql的方法更新数据库，这样就不会影响到数据库中的其他字段
        contractService.update(contract);
        return "redirect:/cargo/contract/list";
    }


    /**
     * 取消合同，将合同的state属性修改为0
     * @param id 需要提交的合同Id
     * @return page
     */
    @RequestMapping("/cancel")
    public String cancelContract(String id) {
        //创建一个合同对象，并将其state属性值设为0
        Contract contract = new Contract();
        contract.setState(0);
        //设置Id值
        contract.setId(id);
        //直接使用动态sql的方法更新数据库，这样就不会影响到数据库中的其他字段
        contractService.update(contract);
        return "redirect:/cargo/contract/list";
    }

    /**
     * 跳转到打印出货表的页面
     * @return page
     */
    @RequestMapping("/print")
    public String jumpToPage() {
        return "cargo/print/contract-print";
    }


}
