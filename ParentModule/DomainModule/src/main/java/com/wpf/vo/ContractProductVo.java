package com.wpf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建时间：2020/11/15
 * SassExport项目-业务数据类，用于存储导出合同出货表中的数据
 * @author wpf
 */
@Data
public class ContractProductVo implements Serializable {

    private String customName;		//客户名称
    private String contractNo;		//合同号，订单号
    private String productNo;		//货号
    private Integer cnumber;		//数量
    private String factoryName;		//厂家名称，冗余字段
    private Date deliveryPeriod;	//交货期限
    private Date shipTime;			//船期
    private String tradeTerms;		//贸易条款

}
