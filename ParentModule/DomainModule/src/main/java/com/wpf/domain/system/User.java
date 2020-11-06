package com.wpf.domain.system;

import lombok.Data;

/**
 * 创建时间：2020/11/6
 * SassExport项目-实体类
 * @author wpf
 */
@Data
public class User {

    private String  id;
    private String  deptId;
    private String  deptName;
    private String  email;
    private String  userName;
    private String  password;

    private Integer state;
    private String  managerId;
    private String  joinDate;
    private Double  salary;
    private String  birthday;
    private String  gender;
    private String  station;
    private String  telephone;

    private Integer degree;
    private String  remark;
    private Integer orderNo;
    private String  companyId;
    private String  companyName;

}
