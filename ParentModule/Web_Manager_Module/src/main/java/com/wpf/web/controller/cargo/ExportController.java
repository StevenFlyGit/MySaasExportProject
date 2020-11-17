package com.wpf.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.wpf.domain.cargo.*;
import com.wpf.domain.system.User;
import com.wpf.service.cargo.ContractService;
import com.wpf.service.cargo.ExportProductService;
import com.wpf.service.cargo.ExportService;
import com.wpf.vo.ExportProductVo;
import com.wpf.vo.ExportResult;
import com.wpf.vo.ExportVo;
import com.wpf.web.controller.BaseController;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
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

    /**
     * 提交合同，将合同的state属性修改为1
     * @param id 需要提交的合同Id
     * @return page
     */
    @RequestMapping("/submit")
    public String submitExport(String id) {
        //创建一个合同对象，并将其state属性值设为1
        Export export = new Export();
        export.setState(1);
        //设置Id值
        export.setId(id);
        //直接使用动态sql的方法更新数据库，这样就不会影响到数据库中的其他字段
        exportService.update(export);
        return "redirect:/cargo/export/list";
    }


    /**
     * 取消合同，将合同的state属性修改为0
     * @param id 需要提交的合同Id
     * @return page
     */
    @RequestMapping("/cancel")
    public String cancelExport(String id) {
        //创建一个合同对象，并将其state属性值设为0
        Export export = new Export();
        export.setState(0);
        //设置Id值
        export.setId(id);
        //直接使用动态sql的方法更新数据库，这样就不会影响到数据库中的其他字段
        exportService.update(export);
        return "redirect:/cargo/export/list";
    }

    @RequestMapping("/exportE")
    public String submitElectricExport(String id) {
        //根据id查询Export数据
        Export export = exportService.findById(id);
        //封装ExportVo对象
        ExportVo exportVo = new ExportVo();
        BeanUtils.copyProperties(export, exportVo);
        //设置Id
        exportVo.setExportId(id);

        //获取结果商品集合，用于后面封装数据
        List<ExportProductVo> productVoList = exportVo.getProducts();

        //根据id查询报运单下的商品信息
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
        if (exportProductList != null && exportProductList.size() > 0) {
            for (ExportProduct exportProduct : exportProductList) {
                ExportProductVo exportProductVo = new ExportProductVo();
                BeanUtils.copyProperties(exportProduct, exportProductVo);
                //设置vo对象中的productId
                exportProductVo.setExportProductId(exportProduct.getId());
                productVoList.add(exportProductVo);
            }
        }

        //通过webservice远程访问海关报运平台
        WebClient.create("http://192.168.85.47:9001/ws/export/user").post(exportVo);
        //获取报运平台处理后的数据
        ExportResult exportResult = WebClient.
                create("http://192.168.85.47:9001/ws/export/user/" + id).get(ExportResult.class);

        //调用dubbo的ExportService服务，修改本地数据库中的报运单数据
        exportService.updateExportFromRemote(exportResult);

        return "redirect:/cargo/export/list";
    }

}
