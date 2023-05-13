package com.wuqisan.ppat.classroom.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.mapper.AnnotationMapper;
import com.wuqisan.ppat.classroom.service.AnnotationService;
import com.wuqisan.ppat.classroom.service.PublicService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnotationServiceImpl implements AnnotationService {
    @Autowired
    PublicService publicService;

    @Autowired
    AnnotationMapper annotationMapper;
    @Autowired
    QuestionService questionService;

    /**
     * @param list
     * @param annotionGroupId
     */
    @Override
    public void addAnnotacionList(List<Annotation> list, Long annotionGroupId) {
        for (Annotation annotation : list) {
            annotation.setAnnotationId(CommonUtils.generateKey15());
            annotation.setAnnotationGroupId(annotionGroupId);
        }
        annotationMapper.addAnnotacionList(list);
    }

    /**
     * @param list
     * @return
     */
    @Override
    public List<Annotation> doHighlightBatch(List<Annotation> list, Question question) {
        for (Annotation annotation : list) {
            if (annotation.getFlag1() == 1) {
                Long  groupId= BaseContext.getUser().getClassroomPart().getGroupId();
                Long subjectId= BaseContext.getUser().getClassroomPart().getSubjectId();
                JSONObject jsonObject = publicService.dealConcepts(annotation.getJsonArray1(),"annotation",groupId,subjectId);
                if (jsonObject.getInteger("highlightFlag") == 1) {
                    JSONArray highlight = jsonObject.getJSONArray("highLightJsonArray");
                    annotation.setHighlightFlag(1);
                    annotation.setHighlightJsonArray(highlight.toJSONString());
                }
                if (jsonObject.getInteger("underlineFlag") == 1) {
                    JSONArray underline = jsonObject.getJSONArray("underlineJsonArray");
                    annotation.setUnderlineFlag(1);
                    annotation.setUnderlineJsonArray(underline.toJSONString());
                }
            }
        }
        return list;
    }

    /**
     * @return
     */
    @Override
    public PageInfo<Annotation> getAnnotationListByAnnotationGroupIdPage(Long annotationGroupId) {
        List<Annotation> list = annotationMapper.getAnnotationByAnnotationGroupId(annotationGroupId);
        return new PageInfo<>(list);
    }

    /**
     * @param annotationId
     * @return
     */
    @Override
    public List<Annotation> getAnnotationListByAnnotationGroupId(Long annotationId) {
        return annotationMapper.getAnnotationByAnnotationGroupId(annotationId);
    }

    /**
     * @param annotationId
     * @return
     */
    @Override
    public List<Annotation> getAnnotationListByQuestionId(Long annotationId) {
        return annotationMapper.getAnnotationByQuestionId(annotationId);
    }

    /**
     * @param groupId
     * @param subjectId
     * @return
     */
    @Override
    public List<Annotation> getAnnotationListByGroupIdAndSubjectId(Long groupId, Long subjectId) {
        return annotationMapper.getAnnotationListBySubjectId(groupId, subjectId);
    }

    /**
     * @param annotation
     * @return
     */
    @Override
    public Long updateQuestionAnnotation(Annotation annotation) {
        Long annotationGroupId = 0L;
        Question questionByQuestionId = questionService.getQuestionByQuestionId(annotation.getQuestionId());
        //1.1创建用于更新的questionbean
        Question question = new Question();
        question.setQuestionId(annotation.getQuestionId());
        question.setAnnotationFlag(1);
        JSONArray annotations = new JSONArray();
        //1.2先判断这个问题有没有注释 如果有先把旧的拿出来
        if (questionByQuestionId.getAnnotationFlag() != null && questionByQuestionId.getAnnotationFlag() == 1) {
            annotations = JSON.parseArray(questionByQuestionId.getAnnotationJsonArray());
        }
        boolean isExists = false;
        for (int i = 0; i < annotations.size(); i++) {
            JSONObject jsonObject = annotations.getJSONObject(i);
            String annotationWord = jsonObject.getString("annotationWord");
            if (StringUtils.equals(annotationWord, annotation.getAnnotationWord())) {
                annotationGroupId = jsonObject.getLong("annotationGroupId");
                isExists = true;
                break;
            }
        }
        //1.3判断注释词里有没有本次要添加的 没有的话的再添加
        if (!isExists) {
            annotationGroupId = CommonUtils.generateKey15();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("annotationWord", annotation.getAnnotationWord());
            jsonObject.put("annotationGroupId", CommonUtils.generateKey15() + "");
            annotations.add(jsonObject);
            question.setAnnotationJsonArray(annotations.toJSONString());
            questionService.updateQuestionByQuestionId(question);
        }
        return annotationGroupId;
    }

    /**
     * @param annotation
     */
    @Override
    public void updateAnnotationByAnnotationId(Annotation annotation) {
        annotationMapper.updateAnnotationByAnnotationId(annotation);
    }
}
