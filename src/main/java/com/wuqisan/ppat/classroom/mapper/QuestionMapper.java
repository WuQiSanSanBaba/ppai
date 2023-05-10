package com.wuqisan.ppat.classroom.mapper;


import com.wuqisan.ppat.classroom.bean.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
    /**
     * 新增问题
     * @param question
     */
    void addQuestion(Question question);


    void updateQuestionByQuestionId(Question question);

    Question getQuestionByQuestionId(Long id);

    List<Question> getHighlightListByGroupIdAndSubjectId(Long groupId, Long subjectId);

     Question getQuestionByPartId(Long partId) ;


}
