package com.wuqisan.ppai.operator.user.controller;

import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.base.controller.BaseController;
import com.wuqisan.ppai.config.aop.authAop.AuthCheck;
import com.wuqisan.ppai.operator.user.bean.MenuInfo;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import com.wuqisan.ppai.operator.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operator/user/")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    /**
     * 登录接口
     * @param userInfo
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("login")
    public R<UserInfo> login(@RequestBody  UserInfo userInfo){
        UserInfo userInfoDB = userService.getUserInfo(userInfo);
        if (userInfoDB==null){
            return R.error("不存在该账号");
        }
        if (!userInfoDB.getPassword().equals(userInfo.getPassword()))
        {
            return R.error("密码不正确");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果6、登录成功，将员工id存入Session并返回登录成功结果
        if (userInfoDB.getStatus()==0)
        {
            return R.error("账号已经禁用");
        }
        getSession().setAttribute("userInfo",userInfo);
        return R.success(userInfoDB);
    }
    @RequestMapping("getMenuList/{id}")
    @ApiOperation(value = "根据用户ID获取其拥有的菜单")
    @AuthCheck("js")
    public R<List<MenuInfo>> getMenuList(@PathVariable Long id){
        List<MenuInfo> menuList = userService.getMenuList(id);
        if (menuList==null){
            return R.error("您没有菜单权限，请联系管理员");
        }else {
            return R.success(menuList);
        }
    }

}
