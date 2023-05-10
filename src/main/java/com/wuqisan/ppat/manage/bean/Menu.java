package com.wuqisan.ppat.manage.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Menu extends BaseBean {
    private String menuId;
    private String parentId;
    private String name;
    private String description;
    private String url;
    private String icon;
}
