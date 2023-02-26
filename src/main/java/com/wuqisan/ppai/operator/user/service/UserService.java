package com.wuqisan.ppai.operator.user.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.operator.user.bean.ClassInfo;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;

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
     * 分压查询所有班级
     * @param pageMap
     * @return
     */
    PageInfo<ClassInfo> getClassList(Map<String, String> pageMap);
}
