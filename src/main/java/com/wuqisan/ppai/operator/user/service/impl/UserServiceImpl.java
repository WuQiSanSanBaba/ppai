package com.wuqisan.ppai.operator.user.service.impl;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.operator.user.bean.ClassInfo;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import com.wuqisan.ppai.operator.user.mapper.UserMapper;
import com.wuqisan.ppai.operator.user.service.UserService;
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
        List<UserInfo> userList = userMapper.getUserList(map);
        return (PageInfo<UserInfo>) new PageInfo(userList);
    }

    /**
     * 分压查询所有班级
     *
     * @param pageMap
     * @return
     */
    @Override
    public PageInfo<ClassInfo> getClassList(Map<String, String> pageMap) {
       List<ClassInfo> classInfos= userMapper.getClassList(pageMap);
        return new PageInfo<>(classInfos);
    }
}
