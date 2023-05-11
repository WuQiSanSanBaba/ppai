package com.wuqisan.ppat.classroom.service;


import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.Question;

import java.util.List;

public interface AnnotacionService {
  void addAnnotacionList(List<Annotation> list);

  List<Annotation> doHighlightBatch(List<Annotation> list, Question question);
}
