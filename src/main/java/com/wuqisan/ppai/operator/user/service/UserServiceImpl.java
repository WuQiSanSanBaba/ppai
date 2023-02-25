package com.wuqisan.ppai.operator.user.service;

import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import com.wuqisan.ppai.operator.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 获取用户信息
     *
     * @param userInfo
     */
    @Override
    public UserInfo getUserInfo(UserInfo userInfo) {
        return userMapper.getUserByUsername(userInfo.getUsername());
    }

    /**
     * 获取用户菜单
     *
     * @param id
     * @return
     */
    @Override
    public List<MenuInfo> getMenuList(Long id) {
        return userMapper.getMenuList(id);
    }
}
