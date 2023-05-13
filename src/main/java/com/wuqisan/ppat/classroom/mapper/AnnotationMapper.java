package com.wuqisan.ppat.classroom.mapper;

import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnnotationMapper {
    void addAnnotationBatchList(List<AnnotationBatch> list);

    List<AnnotationBatch> getAnnotationByQuestionId(Long annotationId);

    Annotation getAnnotationByAnnotationId(Long annotationGroupId);
    List<AnnotationBatch> getAnnotationBatchListByAnnotationId(Long annotationGroupId);

    void updateAnnotationByAnnotationId(AnnotationBatch annotationBatch);

    List<Annotation> getAnnotationListBySubjectId(Long groupId, Long subjectId);

    void addAddAnnotation(Annotation annotation);
}
