package com.wuqisan.ppai.base.context;

import com.wuqisan.ppai.operator.user.bean.UserInfo;

/**
 * 基于ThreadLocal的工具类，用于保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<UserInfo> threadLocal=new ThreadLocal<>();

    public static void setCurrentUserInfo(UserInfo userInfo){
        threadLocal.set(userInfo);
    }

    public static UserInfo getCurrentUserInfo(){
        return threadLocal.get();
    }
}
