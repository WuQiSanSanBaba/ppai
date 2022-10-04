package com.wuqisan.ruijiwaimai.backend.EmployeeMgr.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.backend.EmployeeMgr.mapper.EmployeeMapper;
import com.wuqisan.ruijiwaimai.backend.EmployeeMgr.pojo.Employee;
import com.wuqisan.ruijiwaimai.backend.EmployeeMgr.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
