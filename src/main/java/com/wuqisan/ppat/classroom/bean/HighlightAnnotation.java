package com.wuqisan.ppat.classroom.bean;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class HighlightAnnotation {
    private JSONArray coreJsonArray;
    private JSONArray geneJsonArray;
    private JSONArray addJsonArray;
    private JSONArray underlineJsonArray;
    private JSONArray annotationJsonArray;
}
