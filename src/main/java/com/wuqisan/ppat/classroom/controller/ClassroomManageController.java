package com.wuqisan.ppat.classroom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.Classroom;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.service.ClassroomService;
import com.wuqisan.ppat.classroom.service.ClassroomPartService;
import com.wuqisan.ppat.classroom.service.SubjectService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import com.wuqisan.ppat.common.Utils.ConfigUtils;
import com.wuqisan.ppat.manage.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/classroom/classroomManage/")
@Slf4j
public class ClassroomManageController {
    @Autowired
    ClassroomService classroomService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;
    @Autowired
    ClassroomPartService classroomPartService;

    @PostMapping("addClassroom")
    @ApiOperation("创建课堂")
    public R<String> addClassroom(@RequestBody Classroom classroom) {
        try {
            //获取课堂最大时间
            String classTime = ConfigUtils.getValueById("classTime");
            int timeInterval = Integer.parseInt(classTime);
            classroom.setEffTime(LocalDateTime.now());
            classroom.setExpTime(LocalDateTime.now().plusHours(timeInterval));
            //设置上课老师
            classroom.setTeacherId(BaseContext.getUser().getUserId());
            //设置课堂id
            classroom.setClassroomId(CommonUtils.generateKey15());
            classroomService.addClassroom(classroom);
            //获取被选中的组长们并解析
            List<ClassroomPart> parts = new ArrayList<>();
            JSONArray groupLeaderIds = JSON.parseArray(classroom.getJsonArray1());
            for (int i = 0; i < groupLeaderIds.size(); i++) {
                JSONObject jj= groupLeaderIds.getJSONObject(i);
                ClassroomPart classroomPart=new ClassroomPart();
                classroomPart.setPartId(CommonUtils.generateKey15());
                classroomPart.setGroupId(CommonUtils.generateKey15());
                classroomPart.setClassroomId(classroom.getClassroomId());
                classroomPart.setGroupId(CommonUtils.generateKey15());
                classroomPart.setUserId(Long.parseLong(jj.getString("userId")));
                classroomPart.setUserName(jj.getString("name"));
                classroomPart.setRole("leader");
                classroomPart.setStage(classroom.getStage());
                classroomPart.setEffTime(classroom.getEffTime());
                classroomPart.setExpTime(classroom.getExpTime());
                classroomPart.setStatus(1);
                parts.add(classroomPart);
            }
            classroomPartService.addClassroomPartList(parts);
        } catch (Exception e) {
            log.error("创建课堂失败" + e);
            return R.error("创建课堂失败" + e);
        }
        return R.success("创建课堂成功");
    }



}
