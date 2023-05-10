package com.wuqisan.ppat.manage.service;

import com.wuqisan.ppat.manage.bean.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> getMenuList(Long userId);

}
