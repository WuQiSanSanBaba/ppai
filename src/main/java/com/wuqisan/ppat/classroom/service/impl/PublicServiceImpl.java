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
    public HighlightAnnotation dealConcepts(String coreJsonArrayString, String geneJsonArrayString, String type, Long groupId, Long subjectId) {
        Long userId = BaseContext.getUser().getUserId();
        JSONArray coreJsonArray = new JSONArray();
        if (StringUtils.isNotBlank(coreJsonArrayString)) {
            coreJsonArray = JSON.parseArray(coreJsonArrayString);
        }
        JSONArray geneJsonArray = new JSONArray();
        if (StringUtils.isNotBlank(geneJsonArrayString)) {
            geneJsonArray = JSON.parseArray(geneJsonArrayString);
        }
        JSONArray underlineJsonArray = new JSONArray();
        List<HighlightAnnotation> highlightUnderList;
        //存量问题列表
        List<Question> highLightQuestion = questionService.getHighLightByGroupIdAndSubjectId(groupId, subjectId);
        List<Annotation> highLightAnnotationBatches = annotationService.getAnnotationListByGroupIdAndSubjectId(groupId, subjectId);
        highlightUnderList = this.addAllHighAnnotation(highLightQuestion, highLightAnnotationBatches, type, userId);
        //获取所有被高亮的词
        JSONArray highlightAll = this.getHighlightArray(highlightUnderList);
        //获取所有被注释的词
        JSONArray annotationArray = this.getAnnotationArray(highlightUnderList);
        if (!highlightAll.isEmpty()) {
            for (int i = 0; i < coreJsonArray.size(); i++) {
                String keyword = coreJsonArray.getString(i);
                if (highlightAll.contains(keyword)) {
                    if (annotationArray.contains(keyword)) {
                        underlineJsonArray.add(keyword);
                    } else {
                        coreJsonArray.remove(keyword);
                    }
                }
            }
            for (int i = 0; i < geneJsonArray.size(); i++) {
                String keyword = geneJsonArray.getString(i);
                if (highlightAll.contains(keyword)) {
                    if (annotationArray.contains(keyword)) {
                        underlineJsonArray.add(keyword);
                    } else {
                        geneJsonArray.remove(keyword);
                    }
                }
            }
        }
        HighlightAnnotation highlightAnnotation = new HighlightAnnotation();
        highlightAnnotation.setCoreJsonArray(coreJsonArray);
        highlightAnnotation.setGeneJsonArray(geneJsonArray);
        highlightAnnotation.setUnderlineJsonArray(underlineJsonArray);
        return highlightAnnotation;
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
                addJson.put("flag", "core");
            } else {
                addJson.put("word", add);
                addJson.put("flag", "gene");
            }
            result.add(addJson);
        }

        return result;
    }

    /**
     * @param highlightAnnotation
     * @param content
     * @return
     */
    @Override
    public HighlightAnnotation dealAfterDelete(HighlightAnnotation highlightAnnotation, String content) {
        JSONArray coreJsonArray = highlightAnnotation.getCoreJsonArray();
        coreJsonArray.removeIf(item -> content.contains(item.toString()));
        highlightAnnotation.setCoreJsonArray(coreJsonArray);

        JSONArray geneJsonArray = highlightAnnotation.getGeneJsonArray();
        geneJsonArray.removeIf(item -> content.contains(item.toString()));
        highlightAnnotation.setGeneJsonArray(geneJsonArray);

        JSONArray underlineJsonArray = highlightAnnotation.getUnderlineJsonArray();
        underlineJsonArray.removeIf(item -> content.contains(item.toString()));
        highlightAnnotation.setUnderlineJsonArray(underlineJsonArray);

        JSONArray addJsonArray = highlightAnnotation.getAddJsonArray();
        addJsonArray.removeIf(item -> {
            JSONObject add = (JSONObject) item;
            String word = add.getString("word");
            return content.contains(word);
        });
        highlightAnnotation.setAddJsonArray(addJsonArray);
        return highlightAnnotation;
    }


    private JSONArray allAddHighListAndConcept(List<Question> highLightQuestionList, Subject subjectById) {
        JSONArray jsonArray = new JSONArray();
        for (Question question : highLightQuestionList) {
            //高亮
            jsonArray.addAll(JSON.parseArray(question.getCoreJsonArray()));
            jsonArray.addAll(JSON.parseArray(question.getGeneJsonArray()));
            JSONArray add = JSON.parseArray(question.getAddJsonArray());
            JSONArray addWord = new JSONArray();
            for (int i = 0; i < add.size(); i++) {
                String word = add.getJSONObject(i).getString("word");
                addWord.add(word);
            }
            jsonArray.addAll(addWord);
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
            jsonArray.addAll(highlightAnnotation.getAnnotationJsonArray());
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
            jsonArray.addAll(highlightAnnotation.getCoreJsonArray());
            jsonArray.addAll(highlightAnnotation.getGeneJsonArray());
            jsonArray.addAll(highlightAnnotation.getAddJsonArray());
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
            JSONArray core = JSON.parseArray(question.getCoreJsonArray());
            JSONArray gege = JSON.parseArray(question.getGeneJsonArray());
            JSONArray anno = JSON.parseArray(question.getAnnotationJsonArray());
            JSONArray addWord = new JSONArray();
            JSONArray add = JSON.parseArray(question.getAddJsonArray());
            for (int i = 0; i < add.size(); i++) {
                String word = add.getJSONObject(i).getString("word");
                addWord.add(word);
            }

            highlightAnnotation.setCoreJsonArray(core);
            highlightAnnotation.setGeneJsonArray(gege);
            highlightAnnotation.setAnnotationJsonArray(anno);
            highlightAnnotation.setAddJsonArray(addWord);
            list.add(highlightAnnotation);
        }
        for (Annotation annotation : highLightAnnotationBatches) {
            //操作问题时 不操作自己的
            if (annotation.getCreateUser().equals(userId) && StringUtils.equals(type, "annotation")) {
                continue;
            }
            //高亮
            HighlightAnnotation highlightAnnotation = new HighlightAnnotation();
            JSONArray core = JSON.parseArray(annotation.getCoreJsonArray());
            JSONArray gege = JSON.parseArray(annotation.getGeneJsonArray());
            JSONArray anno = JSON.parseArray(annotation.getAnnotationJsonArray());
            JSONArray add = JSON.parseArray(annotation.getAddJsonArray());
            highlightAnnotation.setCoreJsonArray(core);
            highlightAnnotation.setGeneJsonArray(gege);
            highlightAnnotation.setAnnotationJsonArray(anno);
            highlightAnnotation.setAddJsonArray(add);
            list.add(highlightAnnotation);
            list.add(highlightAnnotation);
        }
        return list;
    }


}
