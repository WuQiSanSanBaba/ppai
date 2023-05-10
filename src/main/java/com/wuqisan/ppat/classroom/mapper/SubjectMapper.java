package com.wuqisan.ppat.classroom.mapper;

import com.wuqisan.ppat.classroom.bean.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubjectMapper {

    List<Subject> getSUbjectList(Map<String, String> params);

    void addSubject(Subject subject);

    void deleteSubject(String subjectId);

    void updateSubjectStatus(Subject subject);

    Subject getSubjectById(Long subjectId);

    void updateSubject(Subject subject);

    List<Subject> getSujectsByStage(String stage);
}
