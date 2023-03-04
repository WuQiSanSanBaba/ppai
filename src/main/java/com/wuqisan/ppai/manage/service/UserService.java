package com.wuqisan.ppai.manage.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.manage.bean.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 查询所有用户信息
     * @param map
     * @return
     */
    public PageInfo<UserInfo> getUserList(Map<String,String> map);

    /**
     * 根据条件获取用户信息
     * @param pageMap
     * @return
     */
    List<UserInfo> getUserByCondition(Map<String, String> pageMap);

    void addUser(UserInfo userInfo);
}
