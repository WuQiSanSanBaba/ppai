package com.wuqisan.ppat.classroom.service;


import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import com.wuqisan.ppat.classroom.bean.Question;

import java.util.List;

public interface AnnotationService {
  Annotation getAnnotationByAnnotationId(Long annotationId);
  void addAnnotationBatchList(List<AnnotationBatch> list, Long annotionGroupId);

  Annotation doHighlightAnnotation(Annotation list);
  PageInfo<AnnotationBatch> getAnnotationBatchListByAnnotationIdPage(Long annotationId);
  List<AnnotationBatch> getAnnotationListByAnnotationBatchByAnnotationId(Long annotationId);
  List<AnnotationBatch> getAnnotationListByQuestionId(Long annotationId);

  List<Annotation> getAnnotationListByGroupIdAndSubjectId(Long groupId, Long subjectId);

  Long updateQuestionAnnotation(String annotationList, Question questionByQuestionId);

  void updateAnnotionBatchListById(List<AnnotationBatch> annotationBatch);

  void addAddAnnotation(Annotation annotation);

  void updateAnnotationByAnnotionId(Annotation annotation);
}
