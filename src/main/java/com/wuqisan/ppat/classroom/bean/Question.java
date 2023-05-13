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
    private Integer addHighlightFlag;
    private String addHighlightJsonArray;
    private String underlineJsonArray;
    private Integer underlineFlag;
    private String annotationJsonArray;
    private Integer annotationFlag;
    private Long subjectId;
    private String subjectName;
    private Long partId;
    private Long groupId;


}
