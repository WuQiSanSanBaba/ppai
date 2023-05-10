package com.wuqisan.ppat.classroom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.service.AnnotacionService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classroom/annotation/")
@Slf4j
public class AnnotationController {
    @Autowired
    AnnotacionService annotacionService;
    @Autowired
    QuestionService questionService;

    @PostMapping("addAnnotation")
    @ApiOperation("添加注释")
    public R<String> addClassroom(@RequestBody List<Annotation> annotationList) {
        annotacionService.addAnnotacionList(annotationList);

        Annotation annotation = annotationList.get(0);
        Question questionByQuestionId = questionService.getQuestionByQuestionId(annotation.getQuestionId());
        Question question = new Question();
        question.setQuestionId(annotation.getQuestionId());
        question.setAnnotationFlag(1);
        JSONArray annotations=new JSONArray();
        if (questionByQuestionId.getAnnotationFlag()!=null&&questionByQuestionId.getAnnotationFlag()==1){
            annotations= JSON.parseArray(questionByQuestionId.getAnnotationJsonArray());
        }
        annotations.add(annotation.getAnnotationWord());
        question.setAnnotationJsonArray(annotations.toJSONString());
        questionService.updateQuestionByQuestionId(question);
        return R.success("1");
    }
}
