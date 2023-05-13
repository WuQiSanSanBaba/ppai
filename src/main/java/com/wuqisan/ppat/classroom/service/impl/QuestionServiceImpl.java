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
     * 处理新增高亮
     *
     * @param questionId
     * @param newArray
     * @return
     */
    @Override
    public Question doAddHighlight(Long questionId, JSONArray newArray) {
        //1、首先根据问题ID把全部信息查出来
        Question question = this.getQuestionByQuestionId(questionId);
        //2.获取已经高亮的JSON数组
        JSONArray highlightArray;
        if (question != null && question.getHighlightFlag() != null && question.getHighlightFlag() == 1) {
            highlightArray = JSON.parseArray(question.getHighlightJsonArray());
        } else {
            highlightArray = new JSONArray();
        }
        //2.1新建一个这次新增高亮的数组
        JSONArray highlightArrayThisNew=new JSONArray();
        //2.2查询存量——黄色高亮
        Long groupId = BaseContext.getUser().getClassroomPart().getGroupId();
        //2.3把同组的问题信息查出来
        List<Question> questionList = questionMapper.getHighlightListByGroupIdAndSubjectId(groupId, question.getSubjectId());
        //2.4如果存量问题里的高亮词已经包含这次新增高亮就作为高亮显示而不是新增高亮显示
        for (Object newItem : newArray) {
            for (Question question_ : questionList) {
                if ("1".equals(question_.getHighlightJsonArray())) {
                    String highLight = question_.getHighlightJsonArray();
                    JSONArray highLightArray = JSON.parseArray(highLight);
                    if (highLightArray.contains(newItem)) {
                        //如果存量有了，使其高亮
                        highlightArrayThisNew.add(newItem);
                        //移除新增数组里的该元素
                        newArray.remove(newItem);
                    }
                }
            }
        }
        //2.5如果原高亮或新高亮有元素 将他们合并并转为JSON字符串
        if (!highlightArray.isEmpty()||!highlightArrayThisNew.isEmpty()) {
            highlightArray.addAll(highlightArrayThisNew);
            question.setHighlightJsonArray(highlightArray.toJSONString());
            question.setHighlightFlag(1);
        } else {
            question.setHighlightFlag(0);
        }
        //2.6如果新增高亮有，就设置新增高亮JSON数组字符串
        if (!newArray.isEmpty()) {
            //把已经存在的新增高亮拿出来
            if (question.getAnnotationFlag()!=null && question.getAddHighlightFlag()==1){
                newArray.addAll(JSON.parseArray(question.getAddHighlightJsonArray()));
            }
            question.setAddHighlightFlag(1);
            question.setAddHighlightJsonArray(newArray.toJSONString());
        } else {
            question.setAddHighlightFlag(0);
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
