package com.wuqisan.ppat.manage.service.impl;

import com.wuqisan.ppat.manage.bean.User;
import com.wuqisan.ppat.manage.mapper.LoginMapper;
import com.wuqisan.ppat.manage.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    /**
     * 获取用户信息
     *
     * @param user
     */
    @Override
    public User getUserInfo(User user) {
        return loginMapper.getUserByUserAccount(user.getUseraccount());
    }

}
