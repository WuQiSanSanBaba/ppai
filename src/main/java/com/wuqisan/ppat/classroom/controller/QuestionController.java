package com.wuqisan.ppat.classroom.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.bean.Subject;
import com.wuqisan.ppat.classroom.service.ClassroomPartService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import com.wuqisan.ppat.classroom.service.SubjectService;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping("addQuestion")
    @ApiOperation("新增问题")
    public R<Question> addQuestion(@RequestBody Question question) {
        this.dealQuestion(question);
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
        this.dealQuestion(question);
        questionService.updateQuestionByQuestionId(question);
        return R.success(question);
    }

    @RequestMapping("getQuestionById/{questionId}")
    @ApiOperation("根据Id查询问题")
    public R<Question> getQuestionById(@PathVariable Long questionId) {
        Question question  = questionService.getQuestionByQuestionId(questionId);
        if (question==null) {
            return R.error("你还没有新建问题");
        }
        Long groupId = BaseContext.getUser().getClassroomPart().getGroupId();
        if ( groupId.equals(question.getGroupId())) {
            List<ClassroomPart> classroomParts= classroomPartService.getGroupMembersByGroupIds(question.getGroupId());
            return R.success(question).add("GroupMembersList",classroomParts);
        } else {
            return R.error("只有同组人员才能查看该问题");
        }
    }
    @RequestMapping("getQuestionBySelf")
    @ApiOperation("查询自己的问题")
    public R<Question> getQuestionBySelf() {
        Long partId  = BaseContext.getUser().getClassroomPart().getPartId();
        //因为questionId唯一，不可能重复的
        //根据组员编号查询问题
        Question question =new Question();
        question.setPartId(partId);
        question = questionService.getQuestionByPartId(partId);
        if (question==null) {
            return R.error("你还没有新建问题");
        }
        List<ClassroomPart> classroomParts= classroomPartService.getGroupMembersByGroupIds(question.getGroupId());
        return R.success(question).add("GroupMembersList",classroomParts);
    }

    @RequestMapping("addHighlight")
    @ApiOperation("新增高亮显示")
    public R<Question> addHighlight(@RequestBody Map<String,Object> body) {
        Long questionId = Long.parseLong((String) body.get("questionId"));
        String newArrayString= (String) body.get("newArrayString");
        JSONArray newArray = JSON.parseArray(newArrayString);
        Question question= questionService.doAddHighlight(questionId,newArray);
        //如果符合非存量的关键词
        if(question.getAddhighlightFlag()==1){
            Subject subjectById = subjectService.getSubjectById(question.getSubjectId());
            JSONArray jsonArray1 = JSON.parseArray(subjectById.getGeneralConceptJsonArray());
            JSONArray jsonArray2 = JSON.parseArray(question.getAddhighlightJsonArray());
            jsonArray1.addAll(jsonArray2);
            subjectById.setGeneralConceptJsonArray(jsonArray1.toJSONString());
            subjectService.updateSubject(subjectById);
        }
        questionService.updateQuestionByQuestionId(question);
        return R.success(question);
    }

    /**
     * 处理问题
     *
     * @param question
     */
    private Question dealQuestion(Question question) {
        JSONArray underlineArr = new JSONArray();
        JSONArray highLightArr = new JSONArray();

        //情况1核心概念和一般概念都没有 不做标记
        if (question.getCoreConceptFlag().equals(1) || question.getGeneralConceptFlag().equals(1)) {
            //将前台传来的核心概念和一般概念合并
            JSONArray conceptsArray = questionService.getAllConcepts(question);
            //查库并处理概念和高亮
            questionService.dealConcepts(question, underlineArr, highLightArr, conceptsArray);
        }
        return question;
    }
}
