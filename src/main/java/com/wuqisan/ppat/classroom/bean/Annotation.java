package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Annotation extends BaseBean {
    private Long annotationId;
    private String annotationWord;
    private Long partId;
    private String questionTitle;
    private Long questionId;
    private Long groupId;
    private Long classroomId;
    private Long subjectId;
    private String annotationType;
    private String coreJsonArray;
    private String geneJsonArray;
    private String addJsonArray;
    private String underlineJsonArray;
    private String annotationJsonArray;

}