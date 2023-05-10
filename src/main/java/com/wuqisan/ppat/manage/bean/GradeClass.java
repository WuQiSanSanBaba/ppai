package com.wuqisan.ppat.manage.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GradeClass extends BaseBean {
    private String classId;
    private String name;
}
