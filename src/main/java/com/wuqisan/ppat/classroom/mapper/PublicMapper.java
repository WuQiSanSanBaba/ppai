package com.wuqisan.ppat.classroom.mapper;

import com.wuqisan.ppat.classroom.bean.Subject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PublicMapper {
    List<Subject> querySubjectByStage(String stage);
}
