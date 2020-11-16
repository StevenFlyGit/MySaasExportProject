package com.wpf.service.cargo;


import com.github.pagehelper.PageInfo;
import com.wpf.domain.cargo.Contract;
import com.wpf.domain.cargo.ContractExample;
import com.wpf.vo.ContractProductVo;

import java.util.List;

/**
 * 购销合同模块
 */
public interface ContractService {

    /**
     * 分页查询
     * @param contractExample 分页查询的参数
     * @param pageNum 当前页
     * @param pageSize 页大小
     * @return
     */
    PageInfo<Contract> findByPage(ContractExample contractExample, int pageNum, int pageSize);

    /**
     * 查询所有
     */
    List<Contract> findAll(ContractExample contractExample);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Contract findById(String id);

    /**
     * 新增
     */
    void save(Contract contract);

    /**
     * 修改
     */
    void update(Contract contract);

    /**
     * 删除部门
     */
    void delete(String id);

    /**
     * 查找某个部门及其子部门的购销合同
     */
    PageInfo<Contract> findByParentDept(String deptId, int pageNum, int pageSize);

    /**
     * 查找某个部门及其子部门的购销合同
     */
    PageInfo<Contract> findByParentDeptAndState(String deptId, int pageNum, int pageSize, Integer state);

    /**
     * 根据船期查找数据
     */
    List<ContractProductVo> findTableVoByShipTime(String shipDateString);
}











