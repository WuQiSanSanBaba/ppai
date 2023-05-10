package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Subject extends BaseBean {
    private Long subjectId;
    private String subjectName;
    private String stage;
    private String coreConceptJsonArray;
    private String generalConceptJsonArray;
}
