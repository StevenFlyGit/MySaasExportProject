package com.wpf.domain.system;

import lombok.*;

import java.util.Date;

/**
 * 创建时间：2020/11/8
 * SassExport项目-实体类
 * @author wpf
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysLog {

    private String id;
    private String userName;
    private String ip;
    private Date time;
    private String method;
    private String action;
    private String companyId;
    private String companyName;

}
