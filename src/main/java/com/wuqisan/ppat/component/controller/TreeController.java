package com.wuqisan.ppat.component.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.common.Utils.ConfigUtils;
import com.wuqisan.ppat.component.service.TreeService;
import com.wuqisan.ppat.manage.bean.GradeClass;
import com.wuqisan.ppat.manage.bean.User;
import com.wuqisan.ppat.manage.service.ClassService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/component/tree/")
public class TreeController {
    @Autowired
    TreeService treeService;
    @Autowired
    ClassService classService;
    @GetMapping("getGradeList")
    @ApiOperation("获取所有班级")
    public R<List<Map<String,Object>>> getGradeList(){
        String gradeNum = ConfigUtils.getValueById("gradeNum");
        List<Map<String,Object>> maps=new ArrayList<>();
        int i = Integer.parseInt(gradeNum);
        for (int j = 1; j <= i; j++) {
            Map<String,Object> map=new HashMap<>();
            map.put("name",j+"年级");
            map.put("value",String.valueOf(j));
            map.put("disabled",true);
            maps.add(map);
        }
        return R.success(maps);
    }

    @GetMapping("getClassList/{id}")
    @ApiOperation("获取所有班级信息")
    public R<List<Map<String, Object>>> getClassList(@PathVariable String id){
        Map<String, String> pageMap=new HashMap<>();
        pageMap.put("classId",id+".");
        List<GradeClass> gradeClassList = classService.getClassList(pageMap);
        List<Map<String, Object>> collect = gradeClassList.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("classId", item.getClassId());
            return map;
        }).collect(Collectors.toList());
        return R.success(collect);
    }
    @GetMapping("getStudentList/{classId}")
    @ApiOperation("获取班级里的所有学生")
    public R<List<Map<String, Object>>> getStudentList(@PathVariable String classId){
        List<User> userList = treeService.getStudentList(classId);
        List<Map<String, Object>> collect = userList.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("userId", item.getUserId());
            return map;
        }).collect(Collectors.toList());
        return R.success(collect);
    }
}
