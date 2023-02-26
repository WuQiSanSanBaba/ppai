package com.wuqisan.ppai.operator.user.controller;

import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.base.controller.BaseController;
import com.wuqisan.ppai.operator.user.bean.UserInfo;
import com.wuqisan.ppai.operator.user.service.LoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operator/login/")
public class LoginController extends BaseController {
    @Autowired
    LoginService loginService;
    /**
     * 登录接口
     * @param userInfo
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("login")
    public R<UserInfo> login(@RequestBody UserInfo userInfo){
        UserInfo userInfoDB = loginService.getUserInfo(userInfo);
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
        getSession().setAttribute("userInfo",userInfoDB);
        return R.success(userInfoDB);
    }
}
