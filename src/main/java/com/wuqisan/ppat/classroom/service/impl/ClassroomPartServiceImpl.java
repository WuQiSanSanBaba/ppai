package com.wuqisan.ppat.classroom.service.impl;

import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.mapper.ClassroomPartMapper;
import com.wuqisan.ppat.classroom.service.ClassroomPartService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author plusthree
 */
@Service
@Slf4j
public class ClassroomPartServiceImpl implements ClassroomPartService {
    @Autowired
    ClassroomPartMapper classroomPartMapper;


    /**
     * @param userId
     * @return
     */
    public ClassroomPart getClassroomPartCurrent(Long userId) {
        return classroomPartMapper.getClassroomPartCurrent(userId);
    }

    /**
     * @param parts
     */
    @Override
    public void addClassroomPartList(List<ClassroomPart> parts) {
        classroomPartMapper.addClassroomPartList(parts);
    }

    /**
     * @param classroomPartList
     * @return
     */
    @Override
    public String addClassroomParts(List<ClassroomPart> classroomPartList) {
        StringBuilder sb = new StringBuilder();

        ClassroomPart classroomPart = BaseContext.getUser().getClassroomPart();
        //把数组里的自己删掉，防止重复
        classroomPartList.removeIf(item ->
                item.getUserId().equals(BaseContext.getUser().getUserId())
        );
        for (ClassroomPart part : classroomPartList) {
            part.setPartId(CommonUtils.generateKey15());
            part.setClassroomId(classroomPart.getClassroomId());
            part.setGroupId(classroomPart.getGroupId());
            part.setRole("member");
            part.setStage(classroomPart.getStage());
            part.setEffTime(classroomPart.getEffTime());
            part.setExpTime(classroomPart.getExpTime());
            part.setStatus(1);
            try {
                classroomPartMapper.addClassroomPart(part);
            } catch (Exception e) {
                sb.append(part.getUserName()).append("已是其他组的成员了;");
                classroomPartMapper.updateClassroomPartGroup(part);
            }
        }
        return sb.toString();
    }

    /**
     * @param classroomPart
     */
    @Override
    public void updateClassroomPartByPartId(ClassroomPart classroomPart) {
        classroomPartMapper.updateClassroomPartByPartId(classroomPart);
    }

    /**
     * @param groupId
     * @return
     */
    @Override
    public List<ClassroomPart> getGroupMembersByGroupIds(Long groupId) {
        return classroomPartMapper.getGroupMembersByGroupIds(groupId);
    }
}
