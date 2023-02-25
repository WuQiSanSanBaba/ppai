package com.wuqisan.ppai.operator.user.bean;

import com.wuqisan.ppai.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuInfo extends BaseBean {
    private Long id;
    private String menuId;
    private String name;
    private String description;
    private String url;
    private String icon;
    private String status;
}
