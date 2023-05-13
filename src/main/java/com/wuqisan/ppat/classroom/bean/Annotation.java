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
    private String subjectName;
    private Integer highlightFlag;
    private Integer underlineFlag;
    private String highlightJsonArray;
    private String underlineJsonArray;
    private String annotationType;
    private String annotationJsonArray;
    private Integer annotationFlag;
    private Integer addHighlightFlag;
    private String addHighlightJsonArray;
}