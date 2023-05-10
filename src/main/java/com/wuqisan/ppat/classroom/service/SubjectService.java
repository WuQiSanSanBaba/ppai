package com.wuqisan.ppat.classroom.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.classroom.bean.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {


    PageInfo<Subject> getSUbjectList(Map<String, String> pageMap);

    void addSubject(Subject subject);

    void deleteSubject(String subjectId);

    void updateSubjectStatus(Subject subject);

    Subject getSubjectById(Long subjectId);

    void updateSubject(Subject subject);

    List<Subject> getSujectsByStage(String stage);
}
