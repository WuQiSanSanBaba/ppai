package com.wuqisan.ppat.classroom.bean;

import lombok.Data;

@Data
public class HighlightAnnotation {
    private String highlightJsonArray;
    private Integer highlightFlag;
    private Integer addHighlightFlag;
    private String addHighlightJsonArray;
    private String underlineJsonArray;
    private Integer underlineFlag;
    private String annotationJsonArray;
    private Integer annotationFlag;
}
