package com.wuqisan.ppat.classroom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.classroom.bean.*;
import com.wuqisan.ppat.classroom.dto.AnnotationDto;
import com.wuqisan.ppat.classroom.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classroom/annotation/")
@Slf4j
public class AnnotationController extends BaseController {
    @Autowired
    AnnotationService annotationService;
    @Autowired
    QuestionService questionService;
    @Autowired
    ClassroomPartService classroomPartService;
    @Autowired
    PublicService publicService;
    @Autowired
    SubjectService subjectService;

    @RequestMapping("getAnnotationListByAnnotationId")
    @ApiOperation("根据annotationId查询所有注释分页")
    public R<AnnotationDto> getAnnotationListByAnnotationId(@RequestBody(required = false) Map<String, String> pageMap) {
        setPage(pageMap);
        Long annotationId = Long.valueOf(pageMap.get("annotationId"));
        PageInfo<AnnotationBatch> anntations = annotationService.getAnnotationBatchListByAnnotationIdPage(annotationId);
        Annotation annotation = annotationService.getAnnotationByAnnotationId(annotationId);
        AnnotationDto annotationDto = new AnnotationDto();
        annotationDto.setAnnotation(annotation);
        annotationDto.setPageInfo(anntations);
        List<ClassroomPart> groupMembersList = classroomPartService.getGroupMembersByGroupIds(annotation.getGroupId());

        return R.success(annotationDto).add("groupMembersList", groupMembersList);
    }

    @PostMapping("updateAnnotation")
    @ApiOperation("添加注释")
    public R<String> updateAnnotation(@RequestBody AnnotationDto annotationDto) {
        Annotation annotation = annotationDto.getAnnotation();
        List<AnnotationBatch> annotationBatcheList = annotationDto.getAnnotationBatchList();
        HighlightAnnotation highlightAnnotation = publicService.dealConcepts(annotation.getCoreJsonArray(), annotation.getGeneJsonArray(),
                "annotation", annotation.getGroupId(), annotation.getSubjectId());
        annotation.setCoreJsonArray(highlightAnnotation.getCoreJsonArray().toJSONString());
        annotation.setGeneJsonArray(highlightAnnotation.getGeneJsonArray().toJSONString());
        annotation.setUnderlineJsonArray(highlightAnnotation.getUnderlineJsonArray().toJSONString());
        annotationService.updateAnnotationByAnnotionId(annotation);
        annotationService.updateAnnotionBatchListById(annotationBatcheList,annotation.getAnnotationId());
        return R.success("修改成功");
    }

    @PostMapping("addAnnotation")
    @ApiOperation("新增注释")
    public R<String> addAnnotation(@RequestBody AnnotationDto annotationDto) {

        //1将注释的内容添加到question表
        Annotation annotation = annotationDto.getAnnotation();
        String annotationWord = annotation.getAnnotationWord();
        List<AnnotationBatch> annotationBatch = annotationDto.getAnnotationBatchList();
        Question questionByQuestionId = questionService.getQuestionByQuestionId(annotation.getQuestionId());
        //2处理获取高亮内容
        annotation = annotationService.doHighlightAnnotation(annotation);
        //更新问题的注释词
        Long annotationId = annotationService.updateQuestionAnnotation(annotationWord, questionByQuestionId);
        annotation.setAnnotationId(annotationId);
        //入库
        //初始化 []
        annotation.setAnnotationJsonArray(new JSONArray().toJSONString());
        annotation.setAddJsonArray(new JSONArray().toJSONString());
        annotationService.addAddAnnotation(annotation);
        //3入库
        annotationService.addAnnotationBatchList(annotationBatch, annotationId);
        return R.success("添加成功");
    }

    @GetMapping("getAnnotationDtoByAnnotationId/{annotationId}")
    @ApiOperation("获取")
    public R<AnnotationDto> getAnnotationsByAnnotationGroupId(@PathVariable Long annotationId) {
        Annotation annotation = annotationService.getAnnotationByAnnotationId(annotationId);
        List<AnnotationBatch> list = annotationService.getAnnotationListByAnnotationBatchByAnnotationId(annotationId);
        AnnotationDto annotationDto = new AnnotationDto();
        annotationDto.setAnnotation(annotation);
        annotationDto.setAnnotationBatchList(list);
        return R.success(annotationDto);
    }
    @GetMapping("deleteAnnotationBatchByAnnotationBatchId/{annotationBatchId}")
    @ApiOperation("删除注释")
    public R<String> deleteAnnotationBatchByAnnotationBatchId(@PathVariable Long annotationBatchId) {
        AnnotationBatch annotationBatch= annotationService.getAnnotationBatchByAnnotationBatchId(annotationBatchId);
        Annotation annotation = annotationService.getAnnotationByAnnotationId(annotationBatch.getAnnotationId());
        HighlightAnnotation highlightAnnotation= annotationService.deleteAnnnotationJsonArray(annotation,annotationBatch.getContent());
        annotation.setCoreJsonArray(highlightAnnotation.getCoreJsonArray().toJSONString());
        annotation.setGeneJsonArray(highlightAnnotation.getGeneJsonArray().toJSONString());
        annotation.setUnderlineJsonArray(highlightAnnotation.getUnderlineJsonArray().toJSONString());
        annotation.setAddJsonArray(highlightAnnotation.getAddJsonArray().toJSONString());
        annotationService.deleteAnnotationBatchByAnnotationBatchId(annotationBatchId);
        return R.success("删除成功");
    }
}
