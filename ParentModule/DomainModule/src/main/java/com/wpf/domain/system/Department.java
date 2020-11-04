package com.wpf.domain.system;

import lombok.*;

/**
 * 创建时间：2020/11/4
 * SassExport项目-实体类
 * @author wpf
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    private String   deptId;
    private String   deptName;
    private String   parentId;
    private Integer  state;
    private String   companyId;
    private String   companyName;
    //通过parent_id来获取上级部门的对象
    private Department parentDept;
}
