package com.wuqisan.ppat.classroom.mapper;

import com.wuqisan.ppat.classroom.bean.Classroom;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassroomMapper {
    void addClassroom(Classroom classroom);


    Classroom getCurrentClassroomByClassroomId(Long classroomId);
}
