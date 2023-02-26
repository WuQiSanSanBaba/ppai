package com.wuqisan.ppai.operator.user.service.impl;

import com.wuqisan.ppai.operator.user.bean.UserInfo;
import com.wuqisan.ppai.operator.user.mapper.LoginMapper;
import com.wuqisan.ppai.operator.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    /**
     * 获取用户信息
     *
     * @param userInfo
     */
    @Override
    public UserInfo getUserInfo(UserInfo userInfo) {
        return loginMapper.getUserByUsername(userInfo.getUsername());
    }

}
