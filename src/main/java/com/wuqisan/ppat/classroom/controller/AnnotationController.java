package com.wuqisan.ppat.classroom.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.AnnotationBatch;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.dto.AnnotationDto;
import com.wuqisan.ppat.classroom.service.AnnotationService;
import com.wuqisan.ppat.classroom.service.ClassroomPartService;
import com.wuqisan.ppat.classroom.service.PublicService;
import com.wuqisan.ppat.classroom.service.QuestionService;
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
        List<ClassroomPart> groupMembersList = classroomPartService.getGroupMembersByGroupIds(anntations.getList().get(0).getGroupId());

        return R.success(annotationDto).add("groupMembersList", groupMembersList);
    }

    @PostMapping("updateAnnotation")
    @ApiOperation("添加注释")
    public R<String> updateAnnotation(@RequestBody AnnotationDto annotationDto) {
        Annotation annotation = annotationDto.getAnnotation();
        List<AnnotationBatch> annotationBatcheList = annotationDto.getAnnotationBatchList();
        //处理新增的符合概念的关键词
        if (annotation.getFlag1() != null && annotation.getFlag1() == 1) {
            JSONObject jsonObject = publicService.dealConcepts(annotation.getJsonArray1(), "annotation"
                    , annotation.getGroupId(), annotation.getSubjectId());
            if (jsonObject.getInteger("highlightFlag") == 1) {
                JSONArray highLightJsonArray = jsonObject.getJSONArray("highLightJsonArray");
                annotation.setHighlightFlag(1);
                annotation.setHighlightJsonArray(highLightJsonArray.toJSONString());
            }
            if (jsonObject.getInteger("underlineFlag") == 1) {
                JSONArray underlineJsonArray = jsonObject.getJSONArray("underlineJsonArray");
                annotation.setUnderlineFlag(1);
                annotation.setUnderlineJsonArray(underlineJsonArray.toJSONString());
            }
        }
        //只要新增的高亮和下划线才更新
        annotationService.updateAnnotationByAnnotionId(annotation);
        annotationService.updateAnnotionBatchListById(annotationBatcheList);
        return R.success("修改成功");
    }

    @PostMapping("addAnnotation")
    @ApiOperation("添加注释")
    public R<String> addAnnotation(@RequestBody AnnotationDto annotationDto) {

        //1将注释的内容添加到question表
        Annotation annotation = annotationDto.getAnnotation();
        String annotationWord = annotation.getAnnotationWord();
        List<AnnotationBatch> annotationBatch = annotationDto.getAnnotationBatchList();
        Question questionByQuestionId = questionService.getQuestionByQuestionId(annotation.getQuestionId());
        //2处理获取高亮内容
        annotation = annotationService.doHighlightAnnotation(annotation, questionByQuestionId, annotation.getGroupId(), annotation.getSubjectId());
        //更新问题的注释词
        Long annotationId = annotationService.updateQuestionAnnotation(annotationWord, questionByQuestionId);
        annotation.setAnnotationId(annotationId);
        //入库
        annotationService.addAddAnnotation(annotation);
        //3入库
        annotationService.addAnnotationBatchList(annotationBatch, annotationId);
        return R.success("添加成功");
    }

    @GetMapping("getAnnotationDtoByAnnotationId/{annotationId}")
    @ApiOperation("添加注释")
    public R<AnnotationDto> getAnnotationsByAnnotationGroupId(@PathVariable Long annotationId) {
        Annotation annotation = annotationService.getAnnotationByAnnotationId(annotationId);
        List<AnnotationBatch> list = annotationService.getAnnotationListByAnnotationBatchByAnnotationId(annotationId);
        AnnotationDto annotationDto = new AnnotationDto();
        annotationDto.setAnnotation(annotation);
        annotationDto.setAnnotationBatchList(list);
        return R.success(annotationDto);
    }
}
