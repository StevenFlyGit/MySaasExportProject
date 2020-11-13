package com.wpf.dao.cargo;

import com.wpf.domain.cargo.Factory;
import com.wpf.domain.cargo.FactoryExample;

import java.util.List;

public interface FactoryDao {
    int deleteByPrimaryKey(String id);

    int insert(Factory record);

    int insertSelective(Factory record);

    List<Factory> selectByExample(FactoryExample example);

    Factory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Factory record);

    int updateByPrimaryKey(Factory record);
}