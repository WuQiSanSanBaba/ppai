package com.wuqisan.ppat.manage.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.common.mapper.QueryMapper;
import com.wuqisan.ppat.manage.bean.User;
import com.wuqisan.ppat.manage.mapper.UserMapper;
import com.wuqisan.ppat.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QueryMapper queryMapper;


    /**
     * 查询所有用户信息
     *
     * @param map
     * @return
     */
    @Override
    public PageInfo<User> getUserList(Map<String, String> map) {
        List<User> userByCondition = queryMapper.getUserByCondition(map);
        return new PageInfo<>(userByCondition);
    }



    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }
}
