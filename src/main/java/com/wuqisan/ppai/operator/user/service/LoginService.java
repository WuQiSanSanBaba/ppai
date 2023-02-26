package com.wuqisan.ppai.operator.user.service;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;

import java.util.List;
import java.util.Map;

public interface LoginService {
    /**
     * 获取用户信息
     */
    public UserInfo getUserInfo(UserInfo userInfo);


}
