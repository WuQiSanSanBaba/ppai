package com.wuqisan.ppat.classroom.controller;

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
        List<ClassroomPart> groupMembersList = classroomPartService.getGroupMembersByGroupIds(anntations.getList().get(0).getGroupId());

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
        //只要新增的高亮和下划线才更新
        annotationService.updateAnnotationByAnnotionId(annotation);
        annotationService.updateAnnotionBatchListById(annotationBatcheList);
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
    /*@RequestMapping("addHighLightAnnotation")
    @ApiOperation("新增高亮显示")
    public R<Question> addHighLightAnnotation(@RequestBody Map<String, Object> body) {
        Long annotationId = Long.parseLong((String) body.get("annotationId"));
        Annotation annotation = annotationService.getAnnotationByAnnotationId(annotationId);
        String newArrayString = (String) body.get("newArrayString");
        JSONArray addHighLightJsonArray = publicService.doAddHighlight(newArrayString, questionByQuestionId.getGroupId(), questionByQuestionId.getSubjectId());
        //处理新增概念
        JSONArray concept = new JSONArray();
        for (int i = 0; i < addHighLightJsonArray.size(); i++) {
            JSONObject jsonObject = addHighLightJsonArray.getJSONObject(i);
            if (StringUtils.equals(jsonObject.getString("flag"), "addHighlight")) {
                concept.add(jsonObject.getString("word"));
            }
        }
        Subject subjectById = subjectService.getSubjectById(questionByQuestionId.getSubjectId());
        JSONArray oldConcept = JSON.parseArray(subjectById.getGeneralConceptJsonArray());
        oldConcept.addAll(concept);
        subjectById.setGeneralConceptJsonArray(oldConcept.toJSONString());
        subjectService.updateSubject(subjectById);
        //更新question
        Question question = new Question();
        question.setQuestionId(questionId);
        question.setQuestionId(question.getQuestionId());
        question.setAddHighlightFlag(1);
        //把旧的高亮和新的合一起
        if (questionByQuestionId.getAddHighlightFlag()!=null&&questionByQuestionId.getAddHighlightFlag()==1){
            addHighLightJsonArray.addAll(JSON.parseArray(questionByQuestionId.getAddHighlightJsonArray()));
        }
        question.setAddHighlightJsonArray(addHighLightJsonArray.toJSONString());
        questionService.updateQuestionByQuestionId(question);
        return R.success(question);
    }*/
}
