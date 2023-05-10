package com.wuqisan.ppat.classroom.service.impl;

import com.wuqisan.ppat.classroom.bean.Classroom;
import com.wuqisan.ppat.classroom.mapper.ClassroomMapper;
import com.wuqisan.ppat.classroom.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    ClassroomMapper classroomMapper;
    @Override
    public void addClassroom(Classroom classroom) {
        //新建课堂
        classroomMapper.addClassroom(classroom);

    }


    /**
     * @param classroomId
     * @return
     */
    @Override
    public Classroom getClassroomByClassroomId(Long classroomId) {
       return classroomMapper.getCurrentClassroomByClassroomId(classroomId);
    }
}
