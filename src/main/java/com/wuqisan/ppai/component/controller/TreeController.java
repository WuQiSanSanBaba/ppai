package com.wuqisan.ppai.component.controller;

import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.common.Utils.ConfigUtils;
import com.wuqisan.ppai.component.service.TreeService;
import com.wuqisan.ppai.manage.bean.ClassInfo;
import com.wuqisan.ppai.manage.service.ClassService;
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
    public R<List<Map<String,String>>> getGradeList(){
        String gradeNum = ConfigUtils.getValueById("gradeNum");
        List<Map<String,String>> maps=new ArrayList<>();
        int i = Integer.parseInt(gradeNum);
        for (int j = 1; j <= i; j++) {
            Map<String,String> map=new HashMap<>();
            map.put("name",j+"年级");
            map.put("value",String.valueOf(j));
            maps.add(map);
        }
        return R.success(maps);
    }

    @GetMapping("getClassList/{id}")
    @ApiOperation("获取所有班级信息")
    public R<List<Map<String, Object>>> getClassList(@PathVariable String id){
        Map<String, String> pageMap=new HashMap<>();
        pageMap.put("classId",id+".");
        List<ClassInfo>  classInfoList= classService.getClassList(pageMap);
        List<Map<String, Object>> collect = classInfoList.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("classId", item.getClassId());
            map.put("leaf", true);
            return map;
        }).collect(Collectors.toList());
        return R.success(collect);
    }
}
