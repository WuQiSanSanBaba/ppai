package com.wuqisan.ppat.classroom.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.bean.Subject;
import com.wuqisan.ppat.classroom.mapper.PublicMapper;
import com.wuqisan.ppat.classroom.service.PublicService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicServiceImpl implements PublicService {
    @Autowired
    QuestionService questionService;
    @Autowired
    PublicMapper publicMapper;
    @Override
    public List<Subject> querySubjectByStage(String stage) {
        return publicMapper.querySubjectByStage(stage);
    }

    /**
     * @param question
     * @param conceptArray
     */
    @Override
    public void dealConcepts(Question question, JSONArray conceptArray) {
        JSONArray underlineArr = new JSONArray();
        JSONArray highLightArr = new JSONArray();
        int underlineFlag = 0;
        int highlightFlag = 0;
        List<Question> highLightQuestion = questionService.getHighLightByGroupIdAndSubjectId();
        //--------------------------------分为如下几种情况--------------------------------------------------------
        //1.为零直接把概念显示为高亮
        if (highLightQuestion.isEmpty()) {
            highLightArr.addAll(conceptArray);
        } else {
            for (Object concept : conceptArray) {
                for (Question dbQuestion : highLightQuestion) {
                    String highLight = dbQuestion.getHighlightJsonArray();
                    String annotation = dbQuestion.getAnnotationJsonArray();
                    Optional<String> optionalStr = Optional.ofNullable(annotation);
                    annotation = optionalStr.orElse("");
                    if (highLight.contains(concept.toString())) {
                        if (annotation.contains(concept.toString())) {
                            underlineArr.add(concept);
                        }
                    } else {
                        highLightArr.add(concept);
                    }
                }
            }

        }
        if (!underlineArr.isEmpty()) {
            underlineFlag = 1;
        }
        if (!highLightArr.isEmpty()) {
            highlightFlag = 1;
        }
        question.setHighlightFlag(highlightFlag);
        question.setUnderlineFlag(underlineFlag);
        question.setHighlightJsonArray(highLightArr.toJSONString());
        question.setUnderlineJsonArray(underlineArr.toJSONString());
    }
}
