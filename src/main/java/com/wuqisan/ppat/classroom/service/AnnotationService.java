package com.wuqisan.ppat.classroom.service;


import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.Question;

import java.util.List;

public interface AnnotationService {
  void addAnnotacionList(List<Annotation> list, Long annotionGroupId);

  List<Annotation> doHighlightBatch(List<Annotation> list, Question question);
  PageInfo<Annotation> getAnnotationListByAnnotationGroupIdPage(Long annotationId);
  List<Annotation> getAnnotationListByAnnotationGroupId(Long annotationId);
  List<Annotation> getAnnotationListByQuestionId(Long annotationId);

  List<Annotation> getAnnotationListByGroupIdAndSubjectId(Long groupId, Long subjectId);

  Long updateQuestionAnnotation(Annotation annotation);

  void updateAnnotationByAnnotationId(Annotation annotation);
}
