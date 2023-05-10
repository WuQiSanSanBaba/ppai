package com.wuqisan.ppat.common.service.impl;

import com.wuqisan.ppat.common.mapper.QueryMapper;
import com.wuqisan.ppat.common.service.QueryService;
import com.wuqisan.ppat.manage.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryServiceImpl implements QueryService {
    @Autowired
    QueryMapper queryMapper;

    /**
     * 根据条件获取用户信息
     *
     * @param pageMap
     * @return
     */
    @Override
    public List<User> getUserByCondition(Map<String, String> pageMap) {
        return queryMapper.getUserByCondition(pageMap);
    }
}
