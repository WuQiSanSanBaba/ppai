package com.wuqisan.ppat.classroom.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.mapper.QuestionMapper;
import com.wuqisan.ppat.classroom.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    /**
     * 新增问题
     *
     * @param question
     */
    @Override
    public void addQuestion(Question question) {
        questionMapper.addQuestion(question);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Question getQuestionByQuestionId(Long id) {
        return questionMapper.getQuestionByQuestionId(id);
    }


    /**
     * 根据小组ID和主题ID高亮问题
     *
     * @return
     */
    @Override
    public List<Question> getHighLightByGroupIdAndSubjectId() {
        ClassroomPart classroomPart = BaseContext.getUser().getClassroomPart();
        if (classroomPart != null) {
            return questionMapper.getHighlightListByGroupIdAndSubjectId(classroomPart.getGroupId(), classroomPart.getSubjectId());
        } else {
            return Collections.emptyList();
        }
    }


    /**
     * 更新问题
     *
     * @param question
     */
    @Override
    public void updateQuestionByQuestionId(Question question) {
        questionMapper.updateQuestionByQuestionId(question);
    }

    /**
     * 处理新增高亮
     *
     * @param questionId
     * @param newArray
     * @return
     */
    @Override
    public Question doAddHighlight(Long questionId, JSONArray newArray) {
        Question question = this.getQuestionByQuestionId(questionId);
        JSONArray highlightArray;
        if (question != null && question.getHighlightFlag() != null && question.getHighlightFlag() == 1) {
            highlightArray = JSON.parseArray(question.getHighlightJsonArray());
        } else {
            highlightArray = new JSONArray();
        }
        //查询存量——黄色高亮
        Long groupId = BaseContext.getUser().getClassroomPart().getGroupId();
        List<Question> questionList = questionMapper.getHighlightListByGroupIdAndSubjectId(groupId, question.getSubjectId());
        for (Object newItem : newArray) {
            for (Question question_ : questionList) {
                if ("1".equals(question_.getHighlightJsonArray())) {
                    String highLight = question_.getHighlightJsonArray();
                    JSONArray highLightArray = JSON.parseArray(highLight);
                    if (highLightArray.contains(newItem)) {
                        //如果存量有了，使其高亮
                        highlightArray.add(newItem);
                        //移除新增数组里的该元素
                        newArray.remove(newItem);
                    }
                }
            }
        }
        if (!highlightArray.isEmpty()) {
            //把已经存在的高亮拿出来
            if (question.getHighlightFlag()==1){
                highlightArray.addAll(JSON.parseArray(question.getHighlightJsonArray()));
            }
            question.setHighlightJsonArray(highlightArray.toJSONString());
            question.setHighlightFlag(1);
        } else {
            question.setHighlightFlag(0);
        }
        if (!newArray.isEmpty()) {
            //把已经存在的新增高亮拿出来
            if (question.getAddhighlightFlag()==1){
                newArray.addAll(JSON.parseArray(question.getAddhighlightJsonArray()));
            }
            question.setAddhighlightFlag(1);
            question.setAddhighlightJsonArray(newArray.toJSONString());
        } else {
            question.setAddhighlightFlag(0);
        }
        return question;
    }

    /**
     * @param partId
     * @return
     */
    @Override
    public Question getQuestionByPartId(Long partId) {
        return questionMapper.getQuestionByPartId(partId);
    }
}
