package com.wpf.service.cargo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.wpf.dao.cargo.ExportProductDao;
import com.wpf.domain.cargo.ExportProduct;
import com.wpf.domain.cargo.ExportProductExample;
import com.wpf.service.cargo.ExportProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 创建时间：2020/11/16
 * SassExport项目-Dubbo服务提供者实现类
 * 处理报运商品的的相关业务
 * @author wpf
 */
@Service(timeout = 5000000)
public class ExportProductServiceImpl implements ExportProductService {

    @Autowired
    ExportProductDao exportProductDao;

    @Override
    public ExportProduct findById(String id) {
        return exportProductDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(ExportProduct exportProduct) {

    }

    @Override
    public void update(ExportProduct exportProduct) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PageInfo<ExportProduct> findByPage(ExportProductExample exportProductExample, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public List<ExportProduct> findAll(ExportProductExample exportProductExample) {
        return exportProductDao.selectByExample(exportProductExample);
    }
}
