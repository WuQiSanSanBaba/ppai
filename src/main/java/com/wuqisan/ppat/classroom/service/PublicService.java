package com.wuqisan.ppat.classroom.service;

import com.alibaba.fastjson.JSONArray;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.bean.Subject;

import java.util.List;

public interface PublicService {
    List<Subject> querySubjectByStage(String stage);
    void dealConcepts(Question question, JSONArray conceptArray);
}
