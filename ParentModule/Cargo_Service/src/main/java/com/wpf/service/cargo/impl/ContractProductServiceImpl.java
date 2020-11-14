package com.wpf.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.cargo.ContractDao;
import com.wpf.dao.cargo.ContractProductDao;
import com.wpf.dao.cargo.ExtCproductDao;
import com.wpf.domain.cargo.Contract;
import com.wpf.domain.cargo.ContractProduct;
import com.wpf.domain.cargo.ContractProductExample;
import com.wpf.domain.cargo.ExtCproduct;
import com.wpf.service.cargo.ContractProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 创建时间：2020/11/14
 * SassExport项目-Dubbo服务提供者实现类
 * 处理合同货物的相关业务
 * @author wpf
 */
@Service(timeout = 5000000)
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    ContractProductDao contractProductDao;
    @Autowired
    ContractDao contractDao;
    @Autowired
    ExtCproductDao extCproductDao;

    @Override
    public PageInfo<ContractProduct> findByPage(ContractProductExample contractProductExample, int pageNum, int pageSize) {
        //启动分页功能
        PageHelper.startPage(pageNum, pageSize);
        //查询列表
        List<ContractProduct> contractList = contractProductDao.selectByExample(contractProductExample);
        //封装pageInfo对象并返回
        return new PageInfo<>(contractList);
    }

    @Override
    public List<ContractProduct> findAll(ContractProductExample contractProductExample) {
        return contractProductDao.selectByExample(contractProductExample);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ContractProduct contractProduct) {
        //设置货物Id
        contractProduct.setId(UUID.randomUUID().toString());

        //计算货物的总金额
        double productTotalPrice = 0;

        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            productTotalPrice = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        contractProduct.setAmount(productTotalPrice);

        //修改购销合同的信息
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //总金额加上货物金额
        contract.setTotalAmount(contract.getTotalAmount() + productTotalPrice);
        //货物数量+1
        contract.setProNum(contract.getProNum() + 1);
        contractDao.updateByPrimaryKeySelective(contract);

        //保存货物数据
        contractProductDao.insertSelective(contractProduct);
    }

    @Override
    public void update(ContractProduct contractProduct) {
        //修改合同中的货物金额
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //查找原来的货物金额
        ContractProduct originProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        Double originTotalPrice = originProduct.getAmount();
        //计算修改后的货物金额
        double TotalPrice = 0;
        if (contractProduct.getCnumber() != null && contractProduct.getPrice() != null) {
            TotalPrice = contractProduct.getCnumber() * contractProduct.getPrice();
            //设置货物的总价
            contractProduct.setAmount(TotalPrice);
        }
        //修改数据库中的合同数据
        contract.setTotalAmount(contract.getTotalAmount() - originTotalPrice + TotalPrice);
        contractDao.updateByPrimaryKeySelective(contract);

        //修改货物数据
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
    }

    @Override
    public void delete(String id) {
        //根据Id查询货物数据
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        //查找对应的合同数据
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //计算货物和附件总金额
        double productAndExtTotalPrice = contractProduct.getAmount();
        //修改合同的货物相关数据
        if (contract != null) {
            List<ExtCproduct> extCproductList = contractProduct.getExtCproducts();
            //货物和附件总金额加上附件总金额
            if (extCproductList != null && extCproductList.size() > 0) {
                for (ExtCproduct exP : extCproductList) {
                    productAndExtTotalPrice += exP.getAmount();
                    //删除数据库中的附件数据
                    extCproductDao.deleteByPrimaryKey(exP.getId());
                }
                if (contract.getExtNum() > 0) {
                    //修改附件数量
                    contract.setExtNum(contract.getExtNum() - extCproductList.size());
                }
            }
            if (contract.getProNum() > 0) {
                //修改货物数量
                contract.setProNum(contract.getProNum() - 1);
            }
            //修改和同总金额
            contract.setTotalAmount(contract.getTotalAmount() - productAndExtTotalPrice);
        }
        //修改数据库中的合同数据
        contractDao.updateByPrimaryKeySelective(contract);
        //删除数据库中的货物数据
        contractProductDao.deleteByPrimaryKey(id);
    }
}
