package com.wpf.dao.cargo;

import com.wpf.domain.cargo.Export;
import com.wpf.domain.cargo.ExportExample;

import java.util.List;

public interface ExportDao {
    /**
     * 根据id删除
     */
    void deleteByPrimaryKey(String id);

    /**
     * 保存
     */
    void insertSelective(Export record);

    /**
     * 条件查询
     */
    List<Export> selectByExample(ExportExample example);

    /**
     * 根据id查询
     */
    Export selectByPrimaryKey(String id);

    /**
     * 更新
     */
    void updateByPrimaryKeySelective(Export record);
}