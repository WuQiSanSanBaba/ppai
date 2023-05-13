package com.wuqisan.ppat.classroom.dto;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import lombok.Data;

import java.util.List;

@Data
public class AnnotationDto<T> {
    private Annotation annotation;
    private PageInfo<T> pageInfo;
    private List<AnnotationBatch> annotationBatchList;
}
