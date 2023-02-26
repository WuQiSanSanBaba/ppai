package com.wuqisan.ppai.operator.user.controller;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.base.controller.BaseController;
import com.wuqisan.ppai.config.aop.authAop.AuthCheck;
import com.wuqisan.ppai.operator.user.bean.ClassInfo;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import com.wuqisan.ppai.operator.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/operator/user/")
public class UserController extends BaseController {
    @Autowired
    UserService userService;



    @RequestMapping("getUserList")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("获取所有用户信息")
    public R<PageInfo<UserInfo>> getUserList(@RequestParam(required = false) Map<String,String> pageMap){
        setPage(pageMap);
        PageInfo<UserInfo> userList = userService.getUserList(pageMap);
        return R.success(userList);
    }
    @RequestMapping("getClassList")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("获取所有班级信息")
    public R<PageInfo<ClassInfo>> getClassList(@RequestParam(required = false) Map<String,String> pageMap){
        setPage(pageMap);
        PageInfo<ClassInfo> classInfoList= userService.getClassList(pageMap);
        return R.success(classInfoList);
    }

}
