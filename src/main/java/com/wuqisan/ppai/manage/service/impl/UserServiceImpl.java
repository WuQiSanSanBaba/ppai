package com.wuqisan.ppai.manage.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.manage.bean.UserInfo;
import com.wuqisan.ppai.manage.mapper.UserMapper;
import com.wuqisan.ppai.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;



    /**
     * 查询所有用户信息
     *
     * @param map
     * @return
     */
    @Override
    public PageInfo<UserInfo> getUserList(Map<String, String> map) {
        List<UserInfo> userByCondition = userMapper.getUserByCondition(map);
        return new PageInfo<>(userByCondition);
    }


    /**
     * 根据条件获取用户信息
     *
     * @param pageMap
     * @return
     */
    @Override
    public List<UserInfo> getUserByCondition(Map<String, String> pageMap) {
        return userMapper.getUserByCondition(pageMap);
    }

    @Override
    public void addUser(UserInfo userInfo) {
        userMapper.addUser(userInfo);
    }
}
