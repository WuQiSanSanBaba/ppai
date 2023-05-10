package com.wuqisan.ppat.base.context;

import com.wuqisan.ppat.manage.bean.User;

/**
 * 基于ThreadLocal的工具类，用于保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static User getUser(){
       return threadLocal.get();
    }
    public static void setUser(User user){
        threadLocal.set(user);
    }


}
