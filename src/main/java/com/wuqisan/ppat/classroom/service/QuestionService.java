package com.wuqisan.ppat.classroom.service;

import com.alibaba.fastjson.JSONArray;
import com.wuqisan.ppat.classroom.bean.Question;

import java.util.List;

public interface QuestionService {
    /**
     * 新增问题
     */
    void addQuestion(Question question);


    Question getQuestionByQuestionId(Long id);
    /**
     * 根据小组ID和主题ID高亮问题
     * @return
     */
    List<Question> getHighLightByGroupIdAndSubjectId(Long groupId, Long subjectId);

    /**
     *更新问题
     * @param question
     */
    void updateQuestionByQuestionId(Question question);

    /**
     * 处理新增高亮
     *
     * @param questionId
     * @param newArray
     * @return
     */
    Question doAddHighlight(Long questionId, JSONArray newArray);

    Question getQuestionByPartId(Long partId);
}
