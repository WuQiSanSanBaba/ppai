package com.wuqisan.ppat.common.service;

import com.wuqisan.ppat.manage.bean.User;

import java.util.List;
import java.util.Map;

public interface QueryService {
    List<User> getUserByCondition(Map<String, String> params);
}
