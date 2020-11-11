package com.wpf.domain.company;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建时间：2020/11/1
 * SassExport项目-实体类
 * @author wpf
 */
@Data
public class Company implements Serializable {
    private String  id;
    private String  name;
    private Date    expirationDate;
    private String  address;
    private String  licenseId;
    private String  representative;
    private String  phone;
    private String  companySize;
    private String  industry;
    private String  remarks;
    private Integer state;
    private Double  balance;
    private String  city;

}
