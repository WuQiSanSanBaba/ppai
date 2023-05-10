package com.wuqisan.ppat.manage.mapper;

import com.wuqisan.ppat.manage.bean.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> getMenuList(Long userId);

}
