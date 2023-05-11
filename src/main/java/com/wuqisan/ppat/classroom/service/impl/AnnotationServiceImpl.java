package com.wuqisan.ppat.classroom.service.impl;


import com.alibaba.fastjson.JSON;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.mapper.AnnotationMapper;
import com.wuqisan.ppat.classroom.service.AnnotacionService;
import com.wuqisan.ppat.classroom.service.PublicService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnotationServiceImpl implements AnnotacionService {
    @Autowired
    PublicService publicService;

    @Autowired
    AnnotationMapper annotationMapper;

    /**
     * @param list
     */
    @Override
    public void addAnnotacionList(List<Annotation> list) {
        for (Annotation annotation : list) {
            annotation.setAnnotationId(CommonUtils.generateKey15());
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
                Question questionTemp=new Question();
                BeanUtils.copyProperties(question,questionTemp);
                publicService.dealConcepts(questionTemp, JSON.parseArray(annotation.getJsonArray1()));
                annotation.setHighlightFlag(questionTemp.getHighlightFlag());
                annotation.setHighlightJsonArray(questionTemp.getHighlightJsonArray());
                annotation.setUnderlineFlag(questionTemp.getUnderlineFlag());
                annotation.setUnderlineJsonArray(questionTemp.getUnderlineJsonArray());
            }
        }
        return list;
    }
}
