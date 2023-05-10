package com.wuqisan.ppat.classroom.service;

import com.wuqisan.ppat.classroom.bean.Subject;

import java.util.List;

public interface PublicService {
    List<Subject> querySubjectByStage(String stage);
}
