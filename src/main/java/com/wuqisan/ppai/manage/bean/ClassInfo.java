package com.wuqisan.ppai.manage.bean;

import com.wuqisan.ppai.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClassInfo extends BaseBean {
    private String classId;
    private String name;
}
