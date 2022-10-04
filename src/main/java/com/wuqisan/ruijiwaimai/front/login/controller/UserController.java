package com.wuqisan.ruijiwaimai.front.login.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import com.wuqisan.ruijiwaimai.front.login.pojo.User;
import com.wuqisan.ruijiwaimai.front.login.service.UserService;
import com.wuqisan.ruijiwaimai.front.login.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> senMsg(@RequestBody User user, HttpServletRequest httpServletRequest){
        Integer integer = null;
        //1没钱发个屌
        if (StringUtils.hasText(user.getPhone()))
        {
             integer = ValidateCodeUtils.generateValidateCode(6);
            log.info("发送验证码成功了捏,验证码是{}",integer);
        }
        //2保存验证码
        httpServletRequest.getSession().setAttribute(user.getPhone(),integer);
        return R.success("发送成功");
    }

    /**
     * 移动端用户端登录
     * @param
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession httpSession){
        log.info("用户登录数据{}",map.toString());
        //直接true呗，反正不能发短信
        if (true) {
            //1验证码比对 比个屁，没有 直接登录成功得了
            //2判断是否在用户表里 没有就给他注册一个
            LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            LambdaQueryWrapper<User> userLambdaQueryWrapper = lambdaQueryWrapper.eq(User::getPhone, map.get("phone"));
            User user = userService.getOne(userLambdaQueryWrapper);
            if (user==null)
            {
                user=new User();
                user.setPhone((String) map.get("phone"));
                user.setStatus(1);
                userService.save(user);
            }
            httpSession.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
