package com.wuqisan.ppat.classroom.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.classroom.bean.Subject;
import com.wuqisan.ppat.classroom.mapper.SubjectMapper;
import com.wuqisan.ppat.classroom.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SubjectServiceImpl implements SubjectService {
   @Autowired
   SubjectMapper subjectMapper;


    @Override
    public PageInfo<Subject> getSUbjectList(Map<String, String> pageMap) {
        List<Subject> list= subjectMapper.getSUbjectList(pageMap);
        return new PageInfo<>(list);
    }


    @Override
    public void addSubject(Subject subject) {
        subjectMapper.addSubject(subject);
    }

    @Override
    public void deleteSubject(String subjectId) {
        subjectMapper.deleteSubject(subjectId);
    }

    @Override
    public void updateSubjectStatus(Subject subject) {
        subjectMapper.updateSubjectStatus(subject);
    }

    @Override
    public Subject getSubjectById(Long subjectId) {
        return subjectMapper.getSubjectById(subjectId);
    }

    @Override
    public void updateSubject(Subject subject) {
        subjectMapper.updateSubject(subject);
    }

    @Override
    public List<Subject> getSujectsByStage(String stage) {
        return subjectMapper.getSujectsByStage(stage);
    }
}
