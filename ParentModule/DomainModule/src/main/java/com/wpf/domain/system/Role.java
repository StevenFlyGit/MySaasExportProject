package com.wpf.domain.system;

import lombok.Data;

/**
 * 创建时间：2020/11/6
 * SassExport项目-实体类
 * @author wpf
 */
@Data
public class Role {

    private String id;
    private String name;
    private String remark;
    private Long orderNo;
    private String companyId;
    private String companyName;

}
