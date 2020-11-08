package com.wpf.domain.system;


import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 创建时间：2020/11/7
 * SassExport项目-实体类
 * @author wpf
 */

@Data
public class Module implements Serializable {
    private String id;
    //父模块id
    private String parentId;
    //父模块名称,冗余字段
    private String parentName;
    //模块名称
    private String name;
    private Integer layerNum;
    private Integer isLeaf;
    private String ico;
    private String cpermission;
    private String curl;
    //0 主菜单/1 左侧菜单/2按钮
    private Integer ctype;
    //1启用0停用
    private Integer state;
    /**
     * 从属关系
     *  0：sass系统内部菜单
     *  1：租用企业菜单
     */ 
    private Integer belong;
    private String cwhich;
    private Integer quoteNum;
    private String remark;
    private Integer orderNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        Module module = (Module) o;
        return Objects.equals(id, module.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}