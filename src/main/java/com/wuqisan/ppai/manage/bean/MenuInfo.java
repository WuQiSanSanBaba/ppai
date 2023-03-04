package com.wuqisan.ppai.manage.bean;

import com.wuqisan.ppai.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuInfo extends BaseBean {
    private String menuId;
    private String parentId;
    private String name;
    private String description;
    private String url;
    private String icon;
}
