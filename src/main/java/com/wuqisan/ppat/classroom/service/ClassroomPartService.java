package com.wuqisan.ppat.classroom.service;

import com.wuqisan.ppat.classroom.bean.ClassroomPart;


import java.util.List;

public interface ClassroomPartService {
    ClassroomPart getClassroomPartCurrent(Long userId);
    void addClassroomPartList(List<ClassroomPart> parts);

    String addClassroomParts(List<ClassroomPart> classroomPartList);

    void updateClassroomPartByPartId(ClassroomPart classroomPart);

    List<ClassroomPart> getGroupMembersByGroupIds(Long groupId);
}
