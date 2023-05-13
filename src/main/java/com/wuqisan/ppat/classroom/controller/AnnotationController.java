package com.wuqisan.ppat.classroom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.classroom.bean.Annotation;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.bean.Question;
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

    @RequestMapping("getAnnotationListByAnnotationGroupIdPage")
    @ApiOperation("根据question查询所有注释")
    public R<PageInfo<Annotation>> getAnnotationListByAnnotationGroupIdPage(@RequestBody(required = false) Map<String, String> pageMap) {
        setPage(pageMap);
        PageInfo<Annotation> anntations = annotationService.getAnnotationListByAnnotationGroupIdPage(Long.valueOf(pageMap.get("annotationGroupId")));
        List<ClassroomPart> groupMembersList = classroomPartService.getGroupMembersByGroupIds(anntations.getList().get(0).getGroupId());

        return R.success(anntations).add("groupMembersList", groupMembersList);
    }

    @PostMapping("editorAnnotation")
    @ApiOperation("添加注释")
    public R<String> editorAnnotation(@RequestBody List<Annotation> annotationList) {
        for (Annotation annotation : annotationList) {
            //处理新增的符合概念的关键词
            if (annotation.getFlag1() != null && annotation.getFlag1() == 1) {
                Long  groupId= BaseContext.getUser().getClassroomPart().getGroupId();
                Long subjectId= BaseContext.getUser().getClassroomPart().getSubjectId();
                JSONObject jsonObject = publicService.dealConcepts(annotation.getJsonArray1(),"annotation",groupId,subjectId);
                if (jsonObject.getInteger("highlightFlag")==1){
                    JSONArray highLightJsonArray = jsonObject.getJSONArray("highLightJsonArray");
                    annotation.setHighlightFlag(1);
                    annotation.setHighlightJsonArray(highLightJsonArray.toJSONString());
                }
                if (jsonObject.getInteger("underlineFlag")==1){
                    JSONArray underlineJsonArray = jsonObject.getJSONArray("underlineJsonArray");
                    annotation.setUnderlineFlag(1);
                    annotation.setUnderlineJsonArray(underlineJsonArray.toJSONString());
                }
            }
            annotationService.updateAnnotationByAnnotationId(annotation);
        }
        return R.success("修改成功");
    }

    @PostMapping("addAnnotation")
    @ApiOperation("添加注释")
    public R<String> addAnnotation(@RequestBody List<Annotation> annotationList) {

        //1将注释的内容添加到question表
        Annotation annotation = annotationList.get(0);
        Question questionByQuestionId = questionService.getQuestionByQuestionId(annotation.getQuestionId());
        //更新问题的注释词
        Long annotionGroupId = annotationService.updateQuestionAnnotation(annotation);
        //2处理获取高亮内容
        annotationService.doHighlightBatch(annotationList, questionByQuestionId);
        //3入库
        annotationService.addAnnotacionList(annotationList,annotionGroupId);
        return R.success("添加成功");
    }

    @GetMapping("getAnnotationsByAnnotationGroupId/{annotationGroupId}")
    @ApiOperation("添加注释")
    public R<List<Annotation>> getAnnotationsByAnnotationGroupId(@PathVariable Long  annotationGroupId) {
        List<Annotation> list = annotationService.getAnnotationListByAnnotationGroupId(annotationGroupId);
        return R.success(list);
    }
}
