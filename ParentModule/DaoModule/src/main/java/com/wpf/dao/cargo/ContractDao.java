package com.wpf.dao.cargo;

import com.wpf.domain.cargo.Contract;
import com.wpf.domain.cargo.ContractExample;

import java.util.List;

public interface ContractDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Contract record);

	//条件查询
    List<Contract> selectByExample(ContractExample example);

	//id查询
    Contract selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Contract record);

    //查询表中本部门及子部门的购销合同数据
    List<Contract> selectByParentDept(String deptId);
}