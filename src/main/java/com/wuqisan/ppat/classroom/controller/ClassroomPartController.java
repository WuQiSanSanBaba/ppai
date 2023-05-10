package com.wuqisan.ppat.classroom.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.service.ClassroomPartService;
import com.wuqisan.ppat.classroom.service.SubjectService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classroom/classroomPart/")
@Slf4j
public class ClassroomPartController {
    @Autowired
    ClassroomPartService classroomPartService;
    @Autowired
    SubjectService subjectService;



    @RequestMapping("saveGroupInfo")
    @ApiOperation("保存小组信息")
    public R<String> saveGroupInfo(@RequestBody List<ClassroomPart> classroomPartList) {

        //新增组员
        String info = classroomPartService.addClassroomParts(classroomPartList);
        if (StringUtils.isBlank(info)) {
            return R.success("保存成功");
        } else {
            return R.error(info + "::已经将他重新分入你的小组");
        }
    }
    @RequestMapping("updateClassroomPartByPartId")
    @ApiOperation("更新课堂成员信息信息")
    public R<String> updateClassroomPartByPartId(@RequestBody ClassroomPart classroomPart){
        classroomPartService.updateClassroomPartByPartId(classroomPart);
        return R.success("更新成功");
    }


}
