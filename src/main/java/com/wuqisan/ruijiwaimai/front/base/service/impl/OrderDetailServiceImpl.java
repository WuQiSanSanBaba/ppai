package com.wuqisan.ruijiwaimai.front.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuqisan.ruijiwaimai.front.base.mapper.OrderDetailMapper;
import com.wuqisan.ruijiwaimai.front.base.pojo.OrderDetail;
import com.wuqisan.ruijiwaimai.front.base.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
