package com.wuqisan.ppat.classroom.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.classroom.bean.Question;
import com.wuqisan.ppat.classroom.mapper.QuestionMapper;
import com.wuqisan.ppat.classroom.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Question> getHighLightByGroupIdAndSubjectId(Long groupId, Long subjectId) {
        return questionMapper.getHighlightListByGroupIdAndSubjectId(groupId, subjectId);
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
     * @param partId
     * @return
     */
    @Override
    public Question getQuestionByPartId(Long partId) {
        return questionMapper.getQuestionByPartId(partId);
    }
}
