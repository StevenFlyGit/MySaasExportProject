package com.wpf.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.cargo.ContractDao;
import com.wpf.domain.cargo.Contract;
import com.wpf.domain.cargo.ContractExample;
import com.wpf.service.cargo.ContractService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 创建时间：2020/11/13
 * SassExport项目-Dubbo服务提供者实现类
 * 处理购销合同的相关业务
 * @author wpf
 */
@Service(timeout = 5000000)
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractDao contractDao;

    @Override
    public PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize) {
        //启动分页功能
        PageHelper.startPage(pageNum, pageSize);
        //查询列表
        List<Contract> contractList = contractDao.selectByExample(contractExample);
        //封装pageInfo对象并返回
        return new PageInfo<>(contractList);
    }

    @Override
    public List<Contract> findAll(ContractExample contractExample) {
        return contractDao.selectByExample(contractExample);
    }

    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        //设置随机Id
        contract.setId(UUID.randomUUID().toString());
        //设置货物、附件、总金额字段为0
        contract.setProNum(0);
        contract.setExtNum(0);
        contract.setTotalAmount(0.0);
        //设置时间
        contract.setCreateTime(new Date());
        //设置购销合同状态为0，表示是草稿
        contract.setState(0);

        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }
}
