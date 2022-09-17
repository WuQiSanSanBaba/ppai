package com.wuqisan.ruijiwaimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wuqisan.ruijiwaimai.controller.pojo.R;
import com.wuqisan.ruijiwaimai.mapper.pojo.Employee;
import com.wuqisan.ruijiwaimai.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 员工登录
     * @param employee
     * @param httpServletRequest
     * @return
     */
    @PostMapping("login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest httpServletRequest){
       //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeService.getOne(queryWrapper);
        //3、如果没有查询到则返回登录失败结果
        if (one==null)
        {
            return R.error("登录失败");
        }
       //4、密码比对，如果不一致则返回登录失败结果
        if (!one.getPassword().equals(password))
        {
            return R.error("登录失败");
        }
       //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果6、登录成功，将员工id存入Session并返回登录成功结果
        if (one.getStatus()==0)
        {
            return R.error("账号已经禁用");
        }
        //6.登陆成功
        httpServletRequest.getSession().setAttribute("employee",one.getId());
        return R.success(one);
    }

    /**
     * 员工退出
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().removeAttribute("employee");
       return R.success("退出成功");
    }

}
