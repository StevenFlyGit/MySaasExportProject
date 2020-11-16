package com.wpf.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.cargo.FactoryDao;
import com.wpf.domain.cargo.Factory;
import com.wpf.domain.cargo.FactoryExample;
import com.wpf.service.cargo.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 创建时间：2020/11/14
 * SassExport项目-Dubbo服务提供者实现类
 * 处理生产厂家的相关业务
 * @author wpf
 */
@Service(timeout = 5000000)
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    FactoryDao factoryDao;

    @Override
    public PageInfo<Factory> findByPage(FactoryExample factoryExample, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<Factory> findAll(FactoryExample factoryExample) {
        return factoryDao.selectByExample(factoryExample);
    }

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Factory factory) {

    }

    @Override
    public void update(Factory factory) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Factory findByName(String factoryName) {
        //创建查询规则对象
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andFactoryNameEqualTo(factoryName);
        //调用内部方法查询相同名称的厂家列表
        List<Factory> factoryList = findAll(example);
        //条件判断，若不为空则返回集合的第一条数据，否则返回null
        return factoryList != null && factoryList.size() > 0 ? factoryList.get(0) : null;
    }
}
