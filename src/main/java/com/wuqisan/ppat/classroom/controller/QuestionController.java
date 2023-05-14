package com.wuqisan.ppat.classroom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.bean.HighlightAnnotation;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.bean.Subject;
import com.wuqisan.ppat.classroom.service.ClassroomPartService;
import com.wuqisan.ppat.classroom.service.PublicService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import com.wuqisan.ppat.classroom.service.SubjectService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author plusthree
 */
@Slf4j
@RestController
@RequestMapping("/classroom/question/")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    ClassroomPartService classroomPartService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    PublicService publicService;

    @RequestMapping("addQuestion")
    @ApiOperation("新增问题")
    public R<Question> addQuestion(@RequestBody Question question) {
        //处理新增的符合概念的关键词
        HighlightAnnotation highlightAnnotation = publicService.dealConcepts(question.getCoreJsonArray(), question.getGeneJsonArray(), "question", question.getGroupId(), question.getSubjectId());
        question.setCoreJsonArray(highlightAnnotation.getCoreJsonArray().toJSONString());
        question.setGeneJsonArray(highlightAnnotation.getGeneJsonArray().toJSONString());
        question.setUnderlineJsonArray(highlightAnnotation.getUnderlineJsonArray().toJSONString());
        //初始化 []
        question.setAnnotationJsonArray(new JSONArray().toJSONString());
        question.setAddJsonArray(new JSONArray().toJSONString());
        //生成id
        question.setQuestionId(CommonUtils.generateKey15());
        questionService.addQuestion(question);
        //更改小组里的问题id
        ClassroomPart classroomPart = new ClassroomPart();
        classroomPart.setPartId(question.getPartId());
        classroomPart.setQuestionId(question.getQuestionId());
        classroomPartService.updateClassroomPartByPartId(classroomPart);

        return R.success(question);
    }

    @RequestMapping("updateQuestion")
    @ApiOperation("更新问题")
    public R<Question> updateQuestion(@RequestBody Question question) {
        Question currentQuestion = questionService.getQuestionByQuestionId(question.getQuestionId());
        //处理新增的符合概念的关键词
        HighlightAnnotation highlightAnnotation = publicService.dealConcepts(question.getCoreJsonArray(), question.getGeneJsonArray(), "question", question.getGroupId(), question.getSubjectId());
        question.setCoreJsonArray(highlightAnnotation.getCoreJsonArray().toJSONString());
        question.setGeneJsonArray(highlightAnnotation.getGeneJsonArray().toJSONString());
        question.setUnderlineJsonArray(highlightAnnotation.getUnderlineJsonArray().toJSONString());
        questionService.updateQuestionByQuestionId(question);
        return R.success(question);
    }

    @RequestMapping("getQuestionById/{questionId}")
    @ApiOperation("根据Id查询问题")
    public R<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.getQuestionByQuestionId(questionId);
        if (question == null) {
            return R.error("你还没有新建问题");
        }
        Long groupId = BaseContext.getUser().getClassroomPart().getGroupId();
        if (groupId.equals(question.getGroupId())) {
            List<ClassroomPart> classroomParts = classroomPartService.getGroupMembersByGroupIds(question.getGroupId());
            return R.success(question).add("groupMembersList", classroomParts);
        } else {
            return R.error("只有同组人员才能查看该问题");
        }
    }

    @RequestMapping("getQuestionBySelf")
    @ApiOperation("查询自己的问题")
    public R<Question> getQuestionBySelf() {
        Long partId = BaseContext.getUser().getClassroomPart().getPartId();
        //因为questionId唯一，不可能重复的
        //根据组员编号查询问题
        Question question = new Question();
        question.setPartId(partId);
        question = questionService.getQuestionByPartId(partId);
        if (question == null) {
            return R.error("你还没有新建问题");
        }
        List<ClassroomPart> classroomParts = classroomPartService.getGroupMembersByGroupIds(question.getGroupId());
        return R.success(question).add("GroupMembersList", classroomParts);
    }

    @RequestMapping("addHighlight")
    @ApiOperation("新增高亮显示")
    public R<Question> addHighlight(@RequestBody Map<String, Object> body) {
        Long questionId = Long.parseLong((String) body.get("questionId"));
        Question questionByQuestionId = questionService.getQuestionByQuestionId(questionId);
        String newArrayString = (String) body.get("newArrayString");
        JSONArray addHighLightJsonArray = publicService.doAddHighlight(newArrayString, questionByQuestionId.getGroupId(), questionByQuestionId.getSubjectId());
        //处理新增概念
        JSONArray concept = new JSONArray();
        for (int i = 0; i < addHighLightJsonArray.size(); i++) {
            JSONObject jsonObject = addHighLightJsonArray.getJSONObject(i);
            if (StringUtils.equals(jsonObject.getString("flag"), "gene")) {
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
        //把旧的高亮和新的合一起
        if (StringUtils.isNotBlank(questionByQuestionId.getAddJsonArray())) {
            addHighLightJsonArray.addAll(JSON.parseArray(questionByQuestionId.getAddJsonArray()));
        }
        question.setAddJsonArray(addHighLightJsonArray.toJSONString());
        questionService.updateQuestionByQuestionId(question);
        return R.success(question);
    }
}
