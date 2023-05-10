package com.wuqisan.ppat.manage.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.manage.bean.User;

import java.util.Map;

public interface UserService {

    /**
     * 查询所有用户信息
     * @param map
     * @return
     */
    public PageInfo<User> getUserList(Map<String,String> map);

    /**
     * 根据条件获取用户信息
     * @param pageMap
     * @return
     */

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
