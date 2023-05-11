package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.base.bean.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Annotation extends BaseBean {
    private Long annotationId;
    private String questionTitle;
    private Long questionId;
    private String annotationWord;
    private String annotationTitle;
    private String content;
    private String html;
    private Long partId;
    private Long groupId;
    private Long classroomId;
    private Long userId;
    private String userName;
    private Long subjectId;
    private String subjectName;

    private Integer highlightFlag;
    private String highlightJsonArray;
    private Integer underlineFlag;
    private String underlineJsonArray;
}
