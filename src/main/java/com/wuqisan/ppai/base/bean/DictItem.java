package com.wuqisan.ppai.base.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictItem extends BaseBean{
    private String dictId;
    private String name;
    private String groupId;
    private String description;
}
