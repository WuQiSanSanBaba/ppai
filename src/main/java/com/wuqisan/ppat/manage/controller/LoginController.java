package com.wuqisan.ppat.manage.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.classroom.bean.Classroom;
import com.wuqisan.ppat.classroom.bean.ClassroomPart;
import com.wuqisan.ppat.classroom.service.ClassroomPartService;
import com.wuqisan.ppat.classroom.service.ClassroomService;
import com.wuqisan.ppat.classroom.service.QuestionService;
import com.wuqisan.ppat.common.Exception.CustomException;
import com.wuqisan.ppat.manage.bean.User;
import com.wuqisan.ppat.manage.service.LoginService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/manage/login/")
@Slf4j
public class LoginController extends BaseController {
    @Autowired
    ClassroomPartService classroomPartService;
    @Autowired
    ClassroomService classroomService;
    @Autowired
    LoginService loginService;
    @Autowired
    QuestionService questionService;

    /**
     * 登录接口
     *
     * @param user
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("login")
    public R<User> login(@RequestBody User user) {
        User userDB = loginService.getUserInfo(user);
        if (userDB == null) {
            return R.error("不存在该账号");
        }
        if (!userDB.getPassword().equals(user.getPassword())) {
            return R.error("密码不正确");
        }
        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果6、登录成功，将员工id存入Session并返回登录成功结果
        if (userDB.getStatus() == 0) {
            return R.error("账号已经禁用");
        }
        getSession().setAttribute("userInfo", userDB);
        return R.success(userDB);
    }

    @PostMapping("logout")
    public R<String> logout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute("userInfo");
        return R.success("退出成功");
    }

    @RequestMapping("getUser")
    @ApiOperation("获取最新用户信息")
    public R<User> getUser() {
        User user = BaseContext.getUser();

        ClassroomPart classroomPart = classroomPartService.getClassroomPartCurrent(user.getUserId());
        if (classroomPart == null) {
            throw new CustomException("你还没有加入课堂或小组");
        } else {
            user.setClassroomPart(classroomPart);
            Classroom classroom= classroomService.getClassroomByClassroomId(classroomPart.getClassroomId());
            user.setClassroom(classroom);
            getSession().setAttribute("userInfo", user);
            return R.success(user);
        }
    }


}
