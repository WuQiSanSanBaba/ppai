package com.wuqisan.ruijiwaimai.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.backend.mapper.EmployeeMapper;
import com.wuqisan.ruijiwaimai.backend.pojo.Employee;
import com.wuqisan.ruijiwaimai.backend.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
