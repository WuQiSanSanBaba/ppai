package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AnnotationBatch extends BaseBean {
    private Long annotationId;
    private Long annotationBatchId;
    private Long questionId;
    private String annotationWord;
    private String annotationTitle;
    private String content;
    private String html;
    private String categorize;
    private Long partId;
    private Long groupId;
    private Long classroomId;

}
