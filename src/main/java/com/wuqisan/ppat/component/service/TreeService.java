package com.wuqisan.ppat.component.service;

import com.wuqisan.ppat.manage.bean.User;

import java.util.List;

public interface TreeService {
    List<User> getStudentList(String classId);
}
