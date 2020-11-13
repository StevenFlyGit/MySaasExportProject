package com.wpf.domain;

import lombok.Data;

import java.util.Date;

/**
 * 创建时间：2020/11/13
 * 系统核心业务实体类的公用实体类
 * @author wpf
 */
@Data
public class BaseEntity {

    protected String createBy;//创建者的id
    protected String createDept;//创建者所在部门的id
    protected Date createTime;//创建时间
    protected String updateBy;//修改者的用户id
    protected Date   updateTime;//更新时间
    protected String companyId;
    protected String companyName;

}
