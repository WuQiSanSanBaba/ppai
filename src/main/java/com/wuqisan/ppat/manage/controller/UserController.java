package com.wuqisan.ppat.manage.controller;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.common.Utils.CommonUtils;
import com.wuqisan.ppat.config.aop.authAop.AuthCheck;
import com.wuqisan.ppat.manage.bean.User;
import com.wuqisan.ppat.manage.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/manage/user/")
@Slf4j
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @RequestMapping("getUserList")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("获取所有用户信息")
    public R<PageInfo<User>> getUserList(@RequestParam(required = false) Map<String, String> pageMap) {
        setPage(pageMap);
        PageInfo<User> userList = userService.getUserList(pageMap);
        return R.success(userList);
    }





    @RequestMapping("addUser")
    @ApiOperation("添加用户信息")
    public R<String> addUser(@RequestBody(required = true) User user) {
        try {
            user.setUserId(CommonUtils.generateKey11());
            userService.addUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("添加失败"+e.getMessage());
        }
        return R.success("添加成功");
    }

    @RequestMapping("updateUser")
    @ApiOperation("修改用户")
    public R<String> updateUser(@RequestBody(required = true) User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("修改失败"+e.getMessage());
        }
        return R.success("修改成功");
    }

    @RequestMapping("deleteUser")
    @ApiOperation("删除用户")
    public R<String> deleteUser(@RequestBody(required = true) User user) {
        try {
            userService.deleteUser(user);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("删除失败"+e.getMessage());
        }
        return R.success("删除成功");
    }

}
