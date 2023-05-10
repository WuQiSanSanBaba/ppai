package com.wuqisan.ppat.manage.controller;

import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.context.BaseContext;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.manage.bean.Menu;
import com.wuqisan.ppat.manage.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manage/menu/")
public class MenuController extends BaseController {
    @Autowired
    MenuService menuService;

    @RequestMapping("getMenuList")
    @ApiOperation(value = "根据用户ID获取其拥有的菜单")
    public R<List<Menu>> getMenuList(){
        List<Menu> menuList = menuService.getMenuList(BaseContext.getUser().getUserId());
        if (menuList==null){
            return R.error("您没有菜单权限，请联系管理员");
        }else {
            return R.success(menuList);
        }
    }
}
