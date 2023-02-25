package com.wuqisan.ppai.operator.user.service;

import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;

import java.util.List;

public interface UserService {
    /**
     * 获取用户信息
     */
    public UserInfo getUserInfo(UserInfo userInfo);

    /**
     * 获取用户菜单
     * @param id
     * @return
     */
    public List<MenuInfo> getMenuList(Long id);
}
