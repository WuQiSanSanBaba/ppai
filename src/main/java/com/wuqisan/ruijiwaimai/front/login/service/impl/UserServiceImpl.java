package com.wuqisan.ruijiwaimai.front.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.front.login.dao.UserMapper;
import com.wuqisan.ruijiwaimai.front.login.pojo.User;
import com.wuqisan.ruijiwaimai.front.login.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
