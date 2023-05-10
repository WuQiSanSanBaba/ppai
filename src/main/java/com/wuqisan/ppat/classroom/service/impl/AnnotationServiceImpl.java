package com.wuqisan.ppat.classroom.service.impl;


import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.mapper.AnnotationMapper;
import com.wuqisan.ppat.classroom.service.AnnotacionService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnotationServiceImpl implements AnnotacionService {
    @Autowired
    AnnotationMapper   annotationMapper;
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
}
