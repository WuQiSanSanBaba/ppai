package com.wuqisan.ppat.common.Utils;

import com.wuqisan.ppat.manage.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CheckUtils {


    @Autowired
    UserMapper userMapper;

    private static CheckUtils CheckUtils;

    @PostConstruct
    public void init(){
        CheckUtils=this;
    }

    public static boolean checkAuth(Long userId,String authId){
        Integer integer = CheckUtils.userMapper.checkAuth(userId, authId);
        return integer != null;
    }
}
