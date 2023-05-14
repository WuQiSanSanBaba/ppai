package com.wuqisan.ppat.classroom.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import com.wuqisan.ppat.classroom.bean.HighlightAnnotation;
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
     * @param annotationId
     * @return
     */
    @Override
    public Annotation getAnnotationByAnnotationId(Long annotationId) {
        return annotationMapper.getAnnotationByAnnotationId(annotationId);
    }

    /**
     * @param list
     */
    @Override
    public void addAnnotationBatchList(List<AnnotationBatch> list, Long annotationId) {
        for (AnnotationBatch annotationBatch : list) {
            annotationBatch.setAnnotationId(annotationId);
            annotationBatch.setAnnotationBatchId(CommonUtils.generateKey15());
        }
        annotationMapper.addAnnotationBatchList(list);
    }

    /**
     * @return
     */
    @Override
    public Annotation doHighlightAnnotation(Annotation annotation) {

        HighlightAnnotation highlightAnnotation = publicService.dealConcepts(annotation.getCoreJsonArray(), annotation.getGeneJsonArray(),
                "annotation", annotation.getGroupId(), annotation.getSubjectId());
        annotation.setCoreJsonArray(highlightAnnotation.getCoreJsonArray().toJSONString());
        annotation.setGeneJsonArray(highlightAnnotation.getGeneJsonArray().toJSONString());
        annotation.setUnderlineJsonArray(highlightAnnotation.getUnderlineJsonArray().toJSONString());
        return annotation;
    }

    /**
     * @return
     */
    @Override
    public PageInfo<AnnotationBatch> getAnnotationBatchListByAnnotationIdPage(Long annotionId) {
        List<AnnotationBatch> list = annotationMapper.getAnnotationBatchListByAnnotationId(annotionId);
        return new PageInfo<>(list);
    }

    /**
     * @param annotationId
     * @return
     */
    @Override
    public List<AnnotationBatch> getAnnotationListByAnnotationBatchByAnnotationId(Long annotationId) {
        return annotationMapper.getAnnotationBatchListByAnnotationId(annotationId);
    }

    /**
     * @param annotationId
     * @return
     */
    @Override
    public List<AnnotationBatch> getAnnotationListByQuestionId(Long annotationId) {
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
     * @param questionByQuestionId
     * @return
     */
    @Override
    public Long updateQuestionAnnotation(String newAnnotationWord, Question questionByQuestionId) {
        Long annotationId = 0L;
        //1.1创建用于更新的questionbean
        Question question = new Question();
        question.setQuestionId(questionByQuestionId.getQuestionId());
        JSONArray annotations = new JSONArray();
        //1.2先判断这个问题有没有注释 如果有先把旧的拿出来
        annotations = JSON.parseArray(questionByQuestionId.getAnnotationJsonArray());
        boolean isExists = false;
        for (int i = 0; i < annotations.size(); i++) {
            JSONObject jsonObject = annotations.getJSONObject(i);
            String annotationWord = jsonObject.getString("annotationWord");
            if (StringUtils.equals(annotationWord, newAnnotationWord)) {
                annotationId = jsonObject.getLong("annotationId");
                isExists = true;
                break;
            }
        }
        //1.3判断注释词里有没有本次要添加的 没有的话的再添加
        if (!isExists) {
            annotationId = CommonUtils.generateKey15();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("annotationWord", newAnnotationWord);
            jsonObject.put("annotationId", annotationId);
            annotations.add(jsonObject);
            question.setAnnotationJsonArray(annotations.toJSONString());
            questionService.updateQuestionByQuestionId(question);
        }
        return annotationId;
    }

    /**
     * @param list
     * @param annotationId
     */
    @Override
    public void updateAnnotionBatchListById(List<AnnotationBatch> list, Long annotationId) {
        for (AnnotationBatch annotationBatch : list) {
            //如果没有id证明是插入
            if (annotationBatch.getAnnotationBatchId() == null) {
                annotationBatch.setAnnotationBatchId(CommonUtils.generateKey15());
                annotationBatch.setAnnotationId(annotationId);
                annotationMapper.addAnnotationBatch(annotationBatch);
            } else {
                annotationMapper.updateAnnotionBatchListById(annotationBatch);
            }
        }
    }

    /**
     * @param annotation
     */
    @Override
    public void addAddAnnotation(Annotation annotation) {
        annotationMapper.addAddAnnotation(annotation);

    }

    /**
     * @param annotation
     */
    @Override
    public void updateAnnotationByAnnotionId(Annotation annotation) {
        annotationMapper.updateAnnotationByAnnotionId(annotation);

    }

    /**
     * @param annotationBatchId
     */
    @Override
    public void deleteAnnotationBatchByAnnotationBatchId(Long annotationBatchId) {
        annotationMapper.deleteAnnotationBatchByAnnotationBatchId(annotationBatchId);
    }

    /**
     * @param annotationBatchId
     * @return
     */
    @Override
    public AnnotationBatch getAnnotationBatchByAnnotationBatchId(Long annotationBatchId) {
       return annotationMapper.getAnnotationBatchByAnnotationBatchId(annotationBatchId);
    }

    /**
     * @param annotation
     * @param content
     * @return
     */
    @Override
    public HighlightAnnotation deleteAnnnotationJsonArray(Annotation annotation, String content) {
        JSONArray core = JSON.parseArray(annotation.getCoreJsonArray());
        JSONArray gene = JSON.parseArray(annotation.getGeneJsonArray());
        JSONArray underline = JSON.parseArray(annotation.getUnderlineJsonArray());
        JSONArray add = JSON.parseArray(annotation.getAddJsonArray());
        HighlightAnnotation highlightAnnotation=new HighlightAnnotation();
        highlightAnnotation.setCoreJsonArray(core);
        highlightAnnotation.setGeneJsonArray(gene);
        highlightAnnotation.setUnderlineJsonArray(underline);
        highlightAnnotation.setAddJsonArray(add);
        highlightAnnotation= publicService.dealAfterDelete(highlightAnnotation,content);
        return highlightAnnotation;
    }
}
