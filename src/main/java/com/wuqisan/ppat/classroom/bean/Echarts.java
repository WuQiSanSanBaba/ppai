package com.wuqisan.ppat.classroom.bean;

import com.wuqisan.ppat.classroom.bean.node.LinkEcharts;
import com.wuqisan.ppat.classroom.bean.node.NodeEcharts;
import lombok.Data;

import java.util.List;

@Data
public class Echarts {
    private List<NodeEcharts> nodeList;
    private List<LinkEcharts> linkList;
}
