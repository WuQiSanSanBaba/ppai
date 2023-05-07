package com.wuqisan.ppat.classroom.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.Echarts;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.service.EchartsService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/classroom/echarts/")
public class EchartsController {
    @Autowired
    EchartsService echartsService;
    @Autowired
    QuestionService questionService;

    @RequestMapping("getEcharts")
    @ApiOperation("获取关系图")
    public R<Echarts> getEcharts() {
        Long partId = BaseContext.getUser().getClassroomPart().getPartId();
        //因为questionId唯一，不可能重复的
        //根据组员编号查询问题
        Question question = new Question();
        question.setPartId(partId);
        question = questionService.getQuestionByPartId(partId);
        if (question == null) {
            return R.error("你还没有新建问题");
        }
        //处理生成echarts数据
        Echarts e = echartsService.geneEchars(question);
        return R.success(e);
    }
}
