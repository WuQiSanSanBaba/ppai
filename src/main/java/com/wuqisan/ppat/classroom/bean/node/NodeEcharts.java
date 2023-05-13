package com.wuqisan.ppat.classroom.bean.node;

import lombok.Data;

@Data
public class NodeEcharts {
    private String id;
    private Long questionId;
    private String questionTitle;
    private Long annotationGroupId;
    private String name;
    private String type;
    private LabelEcharts label=new LabelEcharts(true,"inside");
    private ItemStyleEcharts itemStyle;
    private String symbolSize="30";


}