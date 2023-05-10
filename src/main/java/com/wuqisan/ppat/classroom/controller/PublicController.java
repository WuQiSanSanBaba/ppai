package com.wuqisan.ppat.classroom.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.classroom.bean.Subject;
import com.wuqisan.ppat.classroom.service.PublicService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classroom/public/")
@Slf4j
public class PublicController {

    @Autowired
    PublicService publicService;

    @GetMapping("getStages/{stage}")
    @ApiOperation("根据阶段获取主题列表")
    public R<List<Subject>> querySubjectByStage(@PathVariable String stage){
        List<Subject> list= publicService.querySubjectByStage(stage);
        return R.success(list);
    }

}
