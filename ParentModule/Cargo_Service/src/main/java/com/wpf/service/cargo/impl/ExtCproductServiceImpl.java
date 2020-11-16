package com.wpf.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.cargo.ContractDao;
import com.wpf.dao.cargo.ExtCproductDao;
import com.wpf.domain.cargo.Contract;
import com.wpf.domain.cargo.ContractProduct;
import com.wpf.domain.cargo.ExtCproduct;
import com.wpf.domain.cargo.ExtCproductExample;
import com.wpf.service.cargo.ExtCproductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * 创建时间：2020/11/14
 * SassExport项目-Dubbo服务提供者实现类
 * 处理货物附件的相关业务
 * @author wpf
 */
@Service(timeout = 5000000)
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    ExtCproductDao extCproductDao;
    @Autowired
    ContractDao contractDao;

    @Override
    public PageInfo<ExtCproduct> findByPage(ExtCproductExample extCproductExample, int pageNum, int pageSize) {
        //启动分页功能
        PageHelper.startPage(pageNum, pageSize);
        //查询列表
        List<ExtCproduct> extCproductList = extCproductDao.selectByExample(extCproductExample);
        //封装pageInfo对象并返回
        return new PageInfo<>(extCproductList);
    }

    @Override
    public List<ExtCproduct> findAll(ExtCproductExample extCproductExample) {
        return extCproductDao.selectByExample(extCproductExample);
    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ExtCproduct extCproduct) {
        //设置附件Id
        extCproduct.setId(UUID.randomUUID().toString());
        //计算附件总金额
        double extProductTotalPrice = 0;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            extProductTotalPrice += extCproduct.getCnumber() * extCproduct.getPrice();
        }
        extCproduct.setAmount(extProductTotalPrice);
        //修改合同中的附件信息
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        contract.setExtNum(contract.getExtNum() + 1);
        contract.setTotalAmount(contract.getTotalAmount() + extProductTotalPrice);

        //更新合同数据
        contractDao.updateByPrimaryKeySelective(contract);
        //保存附件数据
        extCproductDao.insertSelective(extCproduct);
    }

    @Override
    public void update(ExtCproduct extCproduct) {
        //修改合同中的附件数据
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //计算附件总金额
        double extProductTotalPrice = 0;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            extProductTotalPrice += extCproduct.getCnumber() * extCproduct.getPrice();
            extCproduct.setAmount(extProductTotalPrice);
        }
        //得到原来的附件总金额
        ExtCproduct originExcProduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //计算修改后的合同总金额并修改
        Double totalAmount = contract.getTotalAmount();
        contract.setTotalAmount(totalAmount - originExcProduct.getAmount() + extProductTotalPrice);
        //更新合同数据
        contractDao.updateByPrimaryKeySelective(contract);

        //修改附件数据
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
    }

    @Override
    public void delete(String id) {
        //根据Id查找附件数据
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);

        //修改合同中的附件数据
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());

        if (contract != null) {
            //修改附件数量
            contract.setExtNum(contract.getExtNum() - 1);
            //修改合同总金额
            contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
        }
        //更新合同数据
        contractDao.updateByPrimaryKeySelective(contract);

        //删除附件数据
        extCproductDao.deleteByPrimaryKey(id);

    }
}
