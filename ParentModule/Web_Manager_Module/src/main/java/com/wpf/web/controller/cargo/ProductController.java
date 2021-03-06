package com.wpf.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.wpf.domain.cargo.ContractProduct;
import com.wpf.domain.cargo.ContractProductExample;
import com.wpf.domain.cargo.Factory;
import com.wpf.domain.cargo.FactoryExample;
import com.wpf.service.cargo.ContractProductService;
import com.wpf.service.cargo.FactoryService;
import com.wpf.web.controller.BaseController;
import com.wpf.web.util.FileUploadUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 创建时间：2020/11/14
 * SassExport项目-Web层-Product控制器
 * @author wpf
 */
@Controller
@RequestMapping("/cargo/contractProduct")
public class ProductController extends BaseController {

    @Reference
    ContractProductService contractProductService;
    @Reference
    FactoryService factoryService;
    //注入解析文件上传的工具类对象
    @Autowired
    FileUploadUtil fileUploadUtil;

    /**
     * 分页查询Contractproduct数据
     * @param pageSize 一页显示的数据数量
     * @param pageNum 当前页码数
     * @return pages
     */
    @RequestMapping("/list")
    public String surfAllProduct(Model model, String contractId,
                                  @RequestParam(defaultValue = "5") Integer pageSize,
                                  @RequestParam(defaultValue = "1") Integer pageNum) {
        //需要获取当用户所属的公司Id
        String companyId = getCompanyId();

        ContractProductExample example = new ContractProductExample();
        //设置查询条件为companyId
        ContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);

        PageInfo<ContractProduct> pageInfo = contractProductService.findByPage(example, pageNum, pageSize);

        //查询下拉列表的厂家数据
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        model.addAttribute("factoryList", factoryList);
        model.addAttribute("contractId", contractId);
        model.addAttribute("pageInfo", pageInfo);
        return "/cargo/product/product-list";
    }

    /**
     * 跳转到修改product的页面，回显数据
     * 将所有的factory数据显示到修改页面中，用于选择生产厂家
     * @param id 需要修改的货物的Id值
     * @return page
     */
    @RequestMapping("/toUpdate")
    public String jumpToUpdatePage(Model model, String id) {
        //查询到需要回显的数据
        ContractProduct product = contractProductService.findById(id);
        //查询下拉列表的厂家数据
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example);

        model.addAttribute("contractProduct", product);
        model.addAttribute("factoryList", factoryList);

        return "/cargo/product/product-update";
    }

    /**
     * 编辑数据的控制器方法，可执行修改或添加操作
     * @param product 需要添加或修改的product对象
     * @param productPhoto 通过表单的文件域获取的文件对象，
     *                     注意要接受此对象必须在前端将form的enctype属性设置为multipart/form-data
     *                     且必须在配置文件中配置spring的文件解析器
     * @return page
     */
    @RequestMapping("/edit")
    public String editProduct(ContractProduct product, MultipartFile productPhoto) {
        //设置货物的企业信息
        product.setCompanyId(getCompanyId());
        product.setCompanyName(getCompanyName());

        //处理文件上传
        if (productPhoto != null && productPhoto.getSize() > 0) {
            try {
                String picUrl = "http://" + fileUploadUtil.upload(productPhoto);
                product.setProductImage(picUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isEmpty(product.getId())) {
            //设置操作用户的相关信息
//            User user = getLoginUser();
//            product.setCreateBy(user.getId());
//            product.setCreateDept(user.getDeptId());
            contractProductService.save(product);
        } else {
            contractProductService.update(product);
        }
        return "redirect:/cargo/contractProduct/list?contractId=" + product.getContractId();
    }

    /**
     * 删除数据的控制器方法
     * @param id 需要删除的数据的Id值
     * @return page
     */
    @RequestMapping("/delete")
    public String removeProduct(String id, String contractId) {
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list?contractId=" + contractId;
    }

    /**
     * 跳转到利用excel表格来上传货物数据的页面
     * @param contractId 货物对应的购销合同Id
     * @return page
     */
    @RequestMapping("/toImport")
    public String jumpToImportPage(Model model, String contractId) {
        model.addAttribute("contractId", contractId);
        return "/cargo/product/product-import";
    }

    /**
     * 上传某个购销合同的货物文件，并将数据保存到数据库
     * @param contractId 货物对应的购销合同Id
     * @param productFile 通过表单的文件域获取的文件对象，
     *                    注意要接受此对象必须在前端将form的enctype属性设置为multipart/form-data
     *                    且必须在配置文件中配置spring的文件解析器
     * @return page
     */
    @RequestMapping("/import")
    public String importProductTable(String contractId, MultipartFile productFile) throws IOException {
        //使用Apache的POI组件来加载excel表格
        Workbook workBook = new XSSFWorkbook(productFile.getInputStream());
        //加载工作表
        Sheet sheet = workBook.getSheetAt(0);
        //获取表格行数
        int rowNum = sheet.getPhysicalNumberOfRows();
        //按照规定的模板，从第二行第二列开始读取数据并封装实体类
        for (int i = 1; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            ContractProduct product = new ContractProduct();
            product.setFactoryName(row.getCell(1).getStringCellValue());
            product.setProductNo(row.getCell(2).getStringCellValue());
            product.setCnumber((int) row.getCell(3).getNumericCellValue());
            product.setPackingUnit(row.getCell(4).getStringCellValue());
            product.setLoadingRate(row.getCell(5).getStringCellValue());
            product.setBoxNum((int) row.getCell(6).getNumericCellValue());
            product.setPrice(row.getCell(7).getNumericCellValue());
            product.setProductDesc(row.getCell(8).getStringCellValue());
            product.setProductRequest(row.getCell(9).getStringCellValue());
            //设置购销合同Id
            product.setContractId(contractId);
            //设置生产厂家Id
            Factory factory = factoryService.findByName(row.getCell(1).getStringCellValue());
            if (factory != null) {
                product.setFactoryId(factory.getId());
            }
            //保存到数据库
            contractProductService.save(product);
        }

        return "redirect:/cargo/contract/list";
    }

}
