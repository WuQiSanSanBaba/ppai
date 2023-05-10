package com.wuqisan.ppat.classroom.service.impl;

import com.wuqisan.ppat.classroom.bean.Subject;
import com.wuqisan.ppat.classroom.mapper.PublicMapper;
import com.wuqisan.ppat.classroom.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicServiceImpl implements PublicService {
    @Autowired
    PublicMapper publicMapper;
    @Override
    public List<Subject> querySubjectByStage(String stage) {
        return publicMapper.querySubjectByStage(stage);
    }
}
