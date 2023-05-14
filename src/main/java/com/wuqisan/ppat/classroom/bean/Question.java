package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Question extends BaseBean {
    private Long questionId;
    private String title;
    private String content;
    private String html;
    private String coreJsonArray;
    private String geneJsonArray;
    private String addJsonArray;
    private String underlineJsonArray;
    private String annotationJsonArray;
    private Long subjectId;
    private Long partId;
    private Long groupId;


}
