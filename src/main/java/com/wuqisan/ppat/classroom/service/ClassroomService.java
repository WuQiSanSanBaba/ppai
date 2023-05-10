package com.wuqisan.ppat.classroom.service;

import com.wuqisan.ppat.classroom.bean.Classroom;

public interface ClassroomService {
    void addClassroom(Classroom classroom);




    Classroom getClassroomByClassroomId(Long classroomId);
}
