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
    private String highlightJsonArray;
    private Integer highlightFlag;
    private Integer addhighlightFlag;
    private String addhighlightJsonArray;
    private String underlineJsonArray;
    private Integer underlineFlag;
    private String annotationJsonArray;
    private Integer annotationFlag;
    private String coreConceptJsonArray;
    private Integer coreConceptFlag;
    private String generalConceptJsonArray;
    private Integer generalConceptFlag;
    private Long subjectId;
    private String subjectName;
    private Long partId;
    private Long groupId;


}
