package com.wpf.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.cargo.*;
import com.wpf.domain.cargo.*;
import com.wpf.service.cargo.ExportService;
import com.wpf.vo.ExportProductResult;
import com.wpf.vo.ExportResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 创建时间：2020/11/16
 * SassExport项目-Dubbo服务提供者实现类
 * 处理合同报运的相关业务
 * @author wpf
 */
@Service(timeout = 5000000)
public class ExportServiceImpl implements ExportService {

    @Autowired
    ExportDao exportDao;
    @Autowired
    ContractDao contractDao;
    @Autowired
    ContractProductDao contractProductDao;
    @Autowired
    ExtCproductDao extCproductDao;
    @Autowired
    ExportProductDao exportProductDao;
    @Autowired
    ExtEproductDao extEproductDao;

    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Export export) {
        //设置Id
        export.setId(UUID.randomUUID().toString());
        //设置创建时间
        export.setCreateTime(new Date());
        //设置状态
        export.setState(0);

        //修改对应购销合同的state属性值为2
        //将export对象中的contractIds字符串转换为List集合
        String[] contractIdsString = export.getContractIds().split(",");
        List<String> contractIdList = Arrays.asList(contractIdsString); //用此方法将数组转换为List集合
        ContractExample contractExample = new ContractExample();
        //设置查找条件
        contractExample.createCriteria().andIdIn(contractIdList);
        //非空校验
        if (contractIdList == null || contractIdList.size() == 0) {
            throw new RuntimeException("未选择购销合同！");
        }
        //查找到对应的contract集合
        List<Contract> contractList = contractDao.selectByExample(contractExample);
        //遍历集合
        for (Contract contract : contractList) {
            //将state属性改为2
            contract.setState(2);

            //获取合同的合同号，并将合同号数据存储到Export的customer_contract属性中，多个和同号之间添加空格
            export.setCustomerContract(export.getCustomerContract() == null ? "" : export.getCustomerContract() +
                    contract.getContractNo() + " ");
            //将修改后的数据保存到数据库
            contractDao.updateByPrimaryKeySelective(contract);
        }

        //创建Map集合，用于存储购销合同货物Id和对应的报运单商品Id
        Map<String, String> map = new HashMap<>(); //第一个String代表货物Id，第二个String代表货物Id

        //查找合同对应的货物表，并将货物表的数据搬运到报运单对应的商品表中
        ContractProductExample contractProductExample = new ContractProductExample();
        //设置查找条件
        contractProductExample.createCriteria().andContractIdIn(contractIdList);
        List<ContractProduct> contractProductList = contractProductDao.selectByExample(contractProductExample);
        //遍历集合
        for (ContractProduct contractProduct : contractProductList) {
            //将数据封装到exportProduct对象
            ExportProduct exportProduct = new ExportProduct();
            BeanUtils.copyProperties(contractProduct, exportProduct);
            //设置商品Id
            exportProduct.setId(UUID.randomUUID().toString());
            //设置报运单Id
            exportProduct.setExportId(export.getId());

            //将货物Id（key）和商品Id（value）封装到Map中
            map.put(contractProduct.getId(), exportProduct.getId());

            //存储数据到商品表
            exportProductDao.insertSelective(exportProduct);
        }

        //查找合同对应的货物附件表，并将货物表的数据搬运到报运单对应的商品附件表中
        ExtCproductExample extCproductExample = new ExtCproductExample();
        //设置查找条件
        extCproductExample.createCriteria().andContractIdIn(contractIdList);
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        //遍历集合
        for (ExtCproduct extCproduct : extCproductList) {
            //将数据封装到extEproduct对象
            ExtEproduct extEproduct = new ExtEproduct();
            BeanUtils.copyProperties(extCproduct, extEproduct);
            //设置附件Id
            extEproduct.setId(UUID.randomUUID().toString());
            //设置对应的报运单Id
            extEproduct.setExportId(export.getId());
            //设置对应的商品Id
            extEproduct.setExportProductId(map.get(extCproduct.getContractProductId()));

            //存储数据到商品附件表
            extEproductDao.insertSelective(extEproduct);
        }

        //设置报运表的商品和附件数量
        export.setProNum(contractProductList.size());
        export.setExtNum(extCproductList.size());
        //将数据添加到报运单表
        exportDao.insertSelective(export);
    }

    @Override
    public void update(Export export) {
        //获取商品集合
        List<ExportProduct> exportProducts = export.getExportProducts();
        if (exportProducts != null && exportProducts.size() > 0){
            for (ExportProduct exportProduct : exportProducts) {
                //保存修改后的商品数据
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
        //保存修改后的报运单数据
        exportDao.updateByPrimaryKeySelective(export);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PageInfo<Export> findByPage(ExportExample example, int pageNum, int pageSize) {
        //启动分页功能
        PageHelper.startPage(pageNum, pageSize);
        //查询列表
        List<Export> contractList = exportDao.selectByExample(example);
        //封装pageInfo对象并返回
        return new PageInfo<Export>(contractList);
    }

    @Override
    public void updateExportFromRemote(ExportResult exportResult) {
        //通过Id查询原来的Export
//        Export originExport = exportDao.selectByPrimaryKey(exportResult.getExportId());
        //直接构造一个新的Export对象并给Id赋值
        Export originExport = new Export();
        originExport.setId(exportResult.getExportId());
        //修改originExport的state和remark属性
        originExport.setState(exportResult.getState());
        originExport.setRemark(exportResult.getRemark());
        exportDao.updateByPrimaryKeySelective(originExport);

        //得到远程返回的货物结果并遍历
        Set<ExportProductResult> products = exportResult.getProducts();
        if (products != null && products.size() > 0) {
            for (ExportProductResult product : products) {
                //构造新的ExportProduct对象来修改数据库中的数据
                ExportProduct exportProduct = new ExportProduct();
                exportProduct.setId(product.getExportProductId());
                exportProduct.setTax(product.getTax());
                //修改数据库的数据
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }
}
