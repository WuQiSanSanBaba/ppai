package com.wuqisan.ppat.classroom.mapper;

import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassroomPartMapper {

    void addClassroomPartList(List<ClassroomPart> parts);

    void addClassroomPart(ClassroomPart part);

    void updateClassroomPartGroup(ClassroomPart part);

    ClassroomPart getClassroomPartCurrent(Long userId);

    void updateClassroomPartByPartId(ClassroomPart classroomPart);

    List<ClassroomPart> getGroupMembersByGroupIds(Long groupId);
}
