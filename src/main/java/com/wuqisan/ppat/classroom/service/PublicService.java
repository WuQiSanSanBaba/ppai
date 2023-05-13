package com.wuqisan.ppat.classroom.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wuqisan.ppat.classroom.bean.Subject;

import java.util.List;

public interface PublicService {
    List<Subject> querySubjectByStage(String stage);
    JSONObject dealConcepts(String jsonArray,String type,Long groupId,Long subjectId);

    JSONArray doAddHighlight(String jsonArray,  Long groupId, Long subjectId);
}
