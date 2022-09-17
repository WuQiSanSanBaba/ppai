package com.wuqisan.ruijiwaimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.mapper.EmployeeMapper;
import com.wuqisan.ruijiwaimai.mapper.pojo.Employee;
import com.wuqisan.ruijiwaimai.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
