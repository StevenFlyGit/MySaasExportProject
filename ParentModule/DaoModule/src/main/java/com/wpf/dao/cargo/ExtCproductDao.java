package com.wpf.dao.cargo;

import com.wpf.domain.cargo.ExtCproduct;
import com.wpf.domain.cargo.ExtCproduct;
import com.wpf.domain.cargo.ExtCproductExample;
import com.wpf.domain.cargo.Factory;

import java.util.List;

public interface ExtCproductDao {

	//删除
	int deleteByPrimaryKey(String id);

	//保存
	int insertSelective(ExtCproduct record);

	//条件查询
	List<ExtCproduct> selectByExample(ExtCproductExample example);

	//id查询
	ExtCproduct selectByPrimaryKey(String id);

	//更新
	int updateByPrimaryKeySelective(ExtCproduct record);

	Factory findByProductId(String productId);
}