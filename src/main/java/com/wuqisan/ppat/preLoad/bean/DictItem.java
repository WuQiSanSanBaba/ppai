package com.wuqisan.ppat.preLoad.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class DictItem extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dictId;
    private String name;
    private String groupId;
    private String description;
}
