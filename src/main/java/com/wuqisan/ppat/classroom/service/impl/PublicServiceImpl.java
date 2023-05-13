package com.wuqisan.ppat.classroom.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.*;
import com.wuqisan.ppat.classroom.mapper.PublicMapper;
import com.wuqisan.ppat.classroom.service.AnnotationService;
import com.wuqisan.ppat.classroom.service.PublicService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import com.wuqisan.ppat.classroom.service.SubjectService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {
    @Autowired
    QuestionService questionService;
    @Autowired
    AnnotationService annotationService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    PublicMapper publicMapper;

    @Override
    public List<Subject> querySubjectByStage(String stage) {
        return publicMapper.querySubjectByStage(stage);
    }

    /**
     *
     */
    @Override
    public JSONObject dealConcepts(String jsonArray, String type, Long groupId, Long subjectId) {
        Long userId = BaseContext.getUser().getUserId();
        JSONArray conceptArray = JSON.parseArray(jsonArray);
        JSONArray underlineArr = new JSONArray();
        JSONArray highLightArr = new JSONArray();
        int underlineFlag = 0;
        int highlightFlag = 0;
        List<HighlightAnnotation> highlightUnderList;
        //存量问题列表
        List<Question> highLightQuestion = questionService.getHighLightByGroupIdAndSubjectId(groupId, subjectId);
        List<Annotation> highLightAnnotationBatches = annotationService.getAnnotationListByGroupIdAndSubjectId(groupId, subjectId);
        highlightUnderList = this.addAllHighAnnotation(highLightQuestion, highLightAnnotationBatches, type, userId);
        //获取所有被高亮的词
        JSONArray highlightAll = this.getHighlightArray(highlightUnderList);
        //获取所有被注释的词
        JSONArray annotationArray = this.getAnnotationArray(highlightUnderList);
        //1.为零直接把概念显示为高亮
        if (highlightAll.isEmpty()) {
            highLightArr.addAll(conceptArray);
        } else {
            for (int i = 0; i < conceptArray.size(); i++) {
                String keyword = conceptArray.getString(i);
                if (highlightAll.contains(keyword)) {
                    if (annotationArray.contains(keyword)) {
                        underlineArr.add(keyword);
                    }
                } else {
                    highLightArr.add(keyword);
                }
            }
        }
        if (!underlineArr.isEmpty()) {
            underlineFlag = 1;
        }
        if (!highLightArr.isEmpty()) {
            highlightFlag = 1;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("highlightFlag", highlightFlag);
        jsonObject.put("underlineFlag", underlineFlag);
        jsonObject.put("highLightJsonArray", highLightArr);
        jsonObject.put("underlineJsonArray", underlineArr);
        return jsonObject;
    }

    /**
     * @param jsonArray
     * @param groupId
     * @param subjectId
     * @return
     */
    @Override
    public JSONArray doAddHighlight(String jsonArray, Long groupId, Long subjectId) {
        //获取存量问题列表
        List<Question> highLightQuestionList = questionService.getHighLightByGroupIdAndSubjectId(groupId, subjectId);
        Subject subjectById = subjectService.getSubjectById(subjectId);
        JSONArray allHighlight = this.allAddHighListAndConcept(highLightQuestionList, subjectById);
        //本次新增的
        JSONArray newArray = JSON.parseArray(jsonArray);
        //用数组存放处理完的的对象
        JSONArray result = new JSONArray();
        for (int i = 0; i < newArray.size(); i++) {
            JSONObject addJson = new JSONObject();
            String add = newArray.getString(i);
            if (allHighlight.contains(add)) {
                addJson.put("word", add);
                addJson.put("flag", "highlight");
            } else {
                addJson.put("word", add);
                addJson.put("flag", "addHighlight");
            }
            result.add(addJson);
        }

        return result;
    }


    private JSONArray allAddHighListAndConcept(List<Question> highLightQuestionList, Subject subjectById) {
        JSONArray jsonArray = new JSONArray();
        for (Question question : highLightQuestionList) {
            //高亮
            if (question.getHighlightFlag() != null && question.getHighlightFlag() == 1) {
                jsonArray.addAll(JSON.parseArray(question.getHighlightJsonArray()));
            }
            //新增高亮
            if (question.getAddHighlightFlag() != null && question.getAddHighlightFlag() == 1) {
                jsonArray.addAll(JSON.parseArray(question.getAddHighlightJsonArray()));
            }
        }
        String coreConceptJsonArray = subjectById.getCoreConceptJsonArray();
        String generalConceptJsonArray = subjectById.getGeneralConceptJsonArray();
        jsonArray.addAll(JSON.parseArray(coreConceptJsonArray));
        jsonArray.addAll(JSON.parseArray(generalConceptJsonArray));
        return jsonArray;
    }

    private JSONArray getAnnotationArray(List<HighlightAnnotation> highlightUnderList) {
        JSONArray jsonArray = new JSONArray();
        for (HighlightAnnotation highlightAnnotation : highlightUnderList) {
            if (highlightAnnotation.getAnnotationFlag() == 1) {
                JSONArray jsonArray1 = JSON.parseArray(highlightAnnotation.getAnnotationJsonArray());
                jsonArray.addAll(jsonArray1);
            }
        }
        return jsonArray;
    }

    /**
     * 获取所有高亮次 包括新增高亮和普通高亮
     *
     * @param highlightUnderList
     * @return
     */
    private JSONArray getHighlightArray(List<HighlightAnnotation> highlightUnderList) {
        JSONArray jsonArray = new JSONArray();
        for (HighlightAnnotation highlightAnnotation : highlightUnderList) {
            if (highlightAnnotation.getHighlightFlag() == 1) {
                JSONArray jsonArray1 = JSON.parseArray(highlightAnnotation.getHighlightJsonArray());
                jsonArray.addAll(jsonArray1);
            }
            if (highlightAnnotation.getAddHighlightFlag() == 1) {
                JSONArray jsonArray1 = JSON.parseArray(highlightAnnotation.getAddHighlightJsonArray());
                jsonArray.addAll(jsonArray1);
            }
        }
        return jsonArray;
    }

    private List<HighlightAnnotation> addAllHighAnnotation(List<Question> highLightQuestion, List<Annotation> highLightAnnotationBatches, String type, Long userId) {
        List<HighlightAnnotation> list = new ArrayList<HighlightAnnotation>();
        for (Question question : highLightQuestion) {
            //操作问题时 不操作自己的
            if (question.getCreateUser().equals(userId) && StringUtils.equals(type, "question")) {
                continue;
            }
            //高亮
            HighlightAnnotation highlightAnnotation = new HighlightAnnotation();
            if (question.getHighlightFlag() != null && question.getHighlightFlag() == 1) {
                highlightAnnotation.setHighlightFlag(1);
                highlightAnnotation.setHighlightJsonArray(question.getHighlightJsonArray());
            } else {
                highlightAnnotation.setHighlightFlag(0);
            }
            //注释
            if (question.getAnnotationFlag() != null && question.getAnnotationFlag() == 1) {
                highlightAnnotation.setAnnotationFlag(1);
                highlightAnnotation.setAnnotationJsonArray(question.getAnnotationJsonArray());
            } else {
                highlightAnnotation.setAnnotationFlag(0);
            }
            //新增高亮
            if (question.getAddHighlightFlag() != null && question.getAddHighlightFlag() == 1) {
                highlightAnnotation.setAddHighlightFlag(1);
                highlightAnnotation.setAddHighlightJsonArray(question.getAddHighlightJsonArray());
            } else {
                highlightAnnotation.setAddHighlightFlag(0);
            }
            list.add(highlightAnnotation);
        }
        for (Annotation annotation : highLightAnnotationBatches) {
            //操作问题时 不操作自己的
            if (annotation.getCreateUser().equals(userId) && StringUtils.equals(type, "annotation")) {
                continue;
            }
            //高亮
            HighlightAnnotation highlightAnnotation = new HighlightAnnotation();
            if (annotation.getHighlightFlag() != null && annotation.getHighlightFlag() == 1) {
                highlightAnnotation.setHighlightFlag(1);
                highlightAnnotation.setHighlightJsonArray(annotation.getHighlightJsonArray());
            } else {
                highlightAnnotation.setHighlightFlag(0);
            }
            //注释
            if (annotation.getAnnotationFlag() != null && annotation.getAnnotationFlag() == 1) {
                highlightAnnotation.setAnnotationFlag(1);
                highlightAnnotation.setAnnotationJsonArray(annotation.getAnnotationJsonArray());
            } else {
                highlightAnnotation.setAnnotationFlag(0);
            }
            //新增高亮
            if (annotation.getAddHighlightFlag() != null && annotation.getAddHighlightFlag() == 1) {
                highlightAnnotation.setAddHighlightFlag(1);
                highlightAnnotation.setAddHighlightJsonArray(annotation.getAddHighlightJsonArray());
            } else {
                highlightAnnotation.setAddHighlightFlag(0);
            }
            list.add(highlightAnnotation);
        }
        return list;
    }


}
