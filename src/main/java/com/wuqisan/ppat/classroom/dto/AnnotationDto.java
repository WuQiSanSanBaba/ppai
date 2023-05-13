package com.wuqisan.ppat.classroom.dto;

import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import lombok.Data;

import java.util.List;

@Data
public class AnnotationDto {
    private Annotation annotation;
    private List<AnnotationBatch> annotationBatchList;
}
