package com.wuqisan.ruijiwaimai.backend.EmployeeMgr.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuqisan.ruijiwaimai.common.pojo.R;
import com.wuqisan.ruijiwaimai.backend.EmployeeMgr.pojo.Employee;
import com.wuqisan.ruijiwaimai.backend.EmployeeMgr.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
    @PostMapping
    public R<String> save(HttpServletRequest httpServletRequest,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
       // employee.setCreateTime(LocalDateTime.now());
      //  employee.setUpdateTime(LocalDateTime.now());
        //获取当前登录的ID
       // Long employeeId = (Long) httpServletRequest.getSession().getAttribute("employee");
       // employee.setCreateUser(employeeId);
       // employee.setUpdateUser(employeeId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工分页信息查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);
        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name),Employee::getName,name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据Id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest httpServletRequest,@RequestBody Employee employee){
        long id = Thread.currentThread().getId();
        log.info("线程id:{}",id);
        log.info("收到的参数是{}",employee.toString()+LocalDateTime.now());
        Long employId= (Long) httpServletRequest.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(employId);
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        return R.success(employee);
    }
}
