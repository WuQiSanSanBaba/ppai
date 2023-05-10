package com.wuqisan.ppat.component.service.impl;

import com.wuqisan.ppat.component.mapper.TreeMapper;
import com.wuqisan.ppat.component.service.TreeService;
import com.wuqisan.ppat.manage.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeServiceImpl implements TreeService {
    @Autowired
    TreeMapper treeMapper;
    @Override
    public List<User> getStudentList(String classId) {
        return treeMapper.getStudentList(classId);
    }
}
