package com.wuqisan.ppat.classroom.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import com.wuqisan.ppat.classroom.bean.Echarts;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.bean.node.ItemStyleEcharts;
import com.wuqisan.ppat.classroom.bean.node.LabelEcharts;
import com.wuqisan.ppat.classroom.bean.node.LinkEcharts;
import com.wuqisan.ppat.classroom.bean.node.NodeEcharts;
import com.wuqisan.ppat.classroom.dto.QuestionAnnotationDto;
import com.wuqisan.ppat.classroom.service.AnnotationService;
import com.wuqisan.ppat.classroom.service.EchartsService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EchartsServiceImpl implements EchartsService {
    @Autowired
    QuestionService questionService;
    @Autowired
    AnnotationService annotationService;


    /**
     * @param partId
     * @return
     */
    @Override
    public Echarts gneneEcharts(Long partId) {
        Echarts echarts = new Echarts();
        Question question = questionService.getQuestionByPartId(partId);
        List<AnnotationBatch> annotationBatch = annotationService.getAnnotationListByQuestionId(question.getQuestionId());
        QuestionAnnotationDto questionAnnotationDto = new QuestionAnnotationDto();
        questionAnnotationDto.setQuestion(question);
        questionAnnotationDto.setAnnotationBatches(annotationBatch);
        //节点列表
        List<NodeEcharts> list = new ArrayList<>();
        List<LinkEcharts> linkEchartsList=new ArrayList<>();
        //生成根节点
        NodeEcharts nodeEcharts = this.geneRootNode(question);
        list.add(nodeEcharts);
        if (question.getAnnotationFlag() != null && question.getAnnotationFlag() == 1) {
            //生成儿子节点
            List<NodeEcharts> childList = this.geneChildNode(question);
            linkEchartsList = this.buildConnections(nodeEcharts, childList);
            //封装所有节点
            list.addAll(childList);
            //建立连接关系
        }
        //组装
        echarts.setLinkList(linkEchartsList);
        echarts.setNodeList(list);
        return echarts;
    }

    private List<LinkEcharts> buildConnections(NodeEcharts root, List<NodeEcharts> childList) {
        List<LinkEcharts> linkEchartsList = new ArrayList<LinkEcharts>();
        for (NodeEcharts childNode : childList) {
            LinkEcharts linkEcharts = new LinkEcharts();
            linkEcharts.setSource(childNode.getId());
            linkEcharts.setTarget(root.getId());
            linkEchartsList.add(linkEcharts);
        }
        return linkEchartsList;
    }

    private List<NodeEcharts> geneChildNode(Question question) {
        List<NodeEcharts> childList = new ArrayList<>();
        JSONArray annotationArray = JSON.parseArray(question.getAnnotationJsonArray());
        for (int i = 0; i < annotationArray.size(); i++) {
            JSONObject jsonObject = annotationArray.getJSONObject(i);
            Long annotationId = jsonObject.getLong("annotationId");
            String annotationWord = jsonObject.getString("annotationWord");
            NodeEcharts nodeEcharts = new NodeEcharts();
            nodeEcharts.setId(CommonUtils.generate15String());
            nodeEcharts.setName(annotationWord);
            nodeEcharts.setAnnotationId(annotationId);
            nodeEcharts.setQuestionTitle(question.getTitle());
            //问题节点50  普通节点30 30在类里默认
            nodeEcharts.setSymbolSize("30");
            nodeEcharts.setQuestionId(question.getQuestionId());
            //设置为根节点
            nodeEcharts.setType("child");
            childList.add(nodeEcharts);
        }
        return childList;
    }

    private NodeEcharts geneRootNode(Question question) {
        NodeEcharts nodeEcharts = new NodeEcharts();
        nodeEcharts.setId(CommonUtils.generate15String());
        nodeEcharts.setName(question.getTitle());
        //问题节点50  普通节点30 30在类里默认
        nodeEcharts.setSymbolSize("50");
        nodeEcharts.setQuestionId(question.getQuestionId());
        //设置为根节点
        nodeEcharts.setType("root");
        //把标题展示在下面
        LabelEcharts labelEcharts = new LabelEcharts();
        labelEcharts.setPosition("bottom");
        labelEcharts.setShow(true);
        nodeEcharts.setLabel(labelEcharts);
        //设置颜色 这里的颜色在commonutils是一个写死的数组
        ItemStyleEcharts itemStyle = new ItemStyleEcharts();
        itemStyle.setColor(CommonUtils.generateRanDomColor());
        nodeEcharts.setItemStyle(itemStyle);
        return nodeEcharts;
    }
}
