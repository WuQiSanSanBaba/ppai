package com.wuqisan.ppat.base.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.classroom.bean.Echarts;
import com.wuqisan.ppat.classroom.bean.node.ItemStyleEcharts;
import com.wuqisan.ppat.classroom.bean.node.LabelEcharts;
import com.wuqisan.ppat.classroom.bean.node.LinkEcharts;
import com.wuqisan.ppat.classroom.bean.node.NodeEcharts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author plusthree
 */
@Slf4j
@RestController
@RequestMapping("/base")
public class TestController {
        @RequestMapping("/test")
        public R<Echarts> test(){
            Echarts data = new Echarts();

            NodeEcharts node=new NodeEcharts();
            node.setId("test");
            node.setName("test");
            node.setType("root");
            node.setQuestionId(123456L);
            ItemStyleEcharts itemStyle=new ItemStyleEcharts();
            itemStyle.setColor(generateColor());
            LabelEcharts labelEcharts=new LabelEcharts();
            labelEcharts.setPosition("bottom");
            labelEcharts.setShow(true);
            node.setItemStyle(itemStyle);
            node.setLabel(labelEcharts);
            node.setSymbolSize("50");

            NodeEcharts node2=new NodeEcharts();
            node2.setId("test2");
            node2.setName("test2");
            node2.setType("son");

            List<NodeEcharts> list=new ArrayList<NodeEcharts>();
            list.add(node);
            list.add(node2);

            LinkEcharts linkEcharts=new LinkEcharts();
            linkEcharts.setSource(node.getId());
            linkEcharts.setTarget(node2.getId());
            List<LinkEcharts> list2=new ArrayList<LinkEcharts>();
            list2.add(linkEcharts);

            data.setNodeList(list);
            data.setLinkList(list2);



            return R.success(data);
        }

        public String generateColor(){
            String[] colors = {"red", "orange", "yellow", "green", "cyan", "blue", "purple"};
            Random random = new Random();
            int index = random.nextInt(colors.length);
            return  colors[index];
        }
}
