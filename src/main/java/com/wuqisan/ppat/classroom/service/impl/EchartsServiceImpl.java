package com.wuqisan.ppat.classroom.service.impl;

import com.wuqisan.ppat.classroom.bean.Echarts;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.bean.node.ItemStyleEcharts;
import com.wuqisan.ppat.classroom.bean.node.LabelEcharts;
import com.wuqisan.ppat.classroom.bean.node.LinkEcharts;
import com.wuqisan.ppat.classroom.bean.node.NodeEcharts;
import com.wuqisan.ppat.classroom.service.EchartsService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EchartsServiceImpl implements EchartsService {
    /**
     * @param question
     * @return
     */
    @Override
    public Echarts geneEchars(Question question) {
        //生成根节点
        NodeEcharts nodeEchartsRoot= this.geneRootNode(question);


        List<NodeEcharts> nodeEchartsList=new ArrayList<NodeEcharts>();
        nodeEchartsList.add(nodeEchartsRoot);

        List<LinkEcharts> linkEchartsList=new ArrayList<LinkEcharts>();

        Echarts echarts=new Echarts();
        echarts.setNodeList(nodeEchartsList);
        echarts.setLinkList(linkEchartsList);

        return echarts;
    }

    private NodeEcharts geneRootNode(Question question) {
        NodeEcharts nodeEcharts=new NodeEcharts();
        nodeEcharts.setId(CommonUtils.generate10String());
        nodeEcharts.setName(question.getTitle());
        //问题节点50  普通节点30 30在类里默认
        nodeEcharts.setSymbolSize("50");
        nodeEcharts.setQuestionId(question.getQuestionId());
        //设置为根节点
        nodeEcharts.setType("root");
        //把标题展示在下面
        LabelEcharts labelEcharts=new LabelEcharts();
        labelEcharts.setPosition("bottom");
        labelEcharts.setShow(true);
        nodeEcharts.setLabel(labelEcharts);
        //设置颜色 这里的颜色在commonutils是一个写死的数组
        ItemStyleEcharts itemStyle=new ItemStyleEcharts();
        itemStyle.setColor(CommonUtils.generateRanDomColor());
        nodeEcharts.setItemStyle(itemStyle);
        return nodeEcharts;
    }
}
