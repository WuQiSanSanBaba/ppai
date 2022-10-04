package com.wuqisan.ruijiwaimai.front.login.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuqisan.ruijiwaimai.front.login.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
