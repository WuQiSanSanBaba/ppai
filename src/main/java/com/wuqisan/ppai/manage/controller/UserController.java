package com.wuqisan.ppai.manage.controller;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.base.controller.BaseController;
import com.wuqisan.ppai.common.Utils.CommonUtils;
import com.wuqisan.ppai.common.Utils.DictUtils;
import com.wuqisan.ppai.config.aop.authAop.AuthCheck;
import com.wuqisan.ppai.manage.bean.UserInfo;
import com.wuqisan.ppai.manage.service.UserService;
import com.wuqisan.ppai.preLoad.bean.DictItem;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public R<PageInfo<UserInfo>> getUserList(@RequestParam(required = false) Map<String, String> pageMap) {
        setPage(pageMap);
        PageInfo<UserInfo> userList = userService.getUserList(pageMap);
        return R.success(userList);
    }

    @RequestMapping("getTypeList/{groupId}")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("获取角色列表")
    public R<List<DictItem>> getTypeList(@PathVariable String groupId) {
        return R.success(DictUtils.getGroupList(groupId));
    }

    @RequestMapping("getUserByCondition")
    @ApiOperation("根据条件获取用户信息")
    public R<List<UserInfo>> getUserByCondition(@RequestBody(required = false) Map<String, String> params) {
        List<UserInfo> userList = userService.getUserByCondition(params);
        return R.success(userList);
    }

    @RequestMapping("addUser")
    @ApiOperation("添加用户信息")
    public R<String> addUser(@RequestBody(required = true) UserInfo userInfo) {
        try {
            userInfo.setUserId(CommonUtils.generateKey());
            userService.addUser(userInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            return R.error("添加失败"+e.getMessage());
        }
        return R.success("添加成功");
    }

}
