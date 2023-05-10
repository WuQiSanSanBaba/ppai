package com.wuqisan.ppat.system.controller;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.common.Utils.CacheUtils;
import com.wuqisan.ppat.config.aop.authAop.AuthCheck;
import com.wuqisan.ppat.system.bean.Config;
import com.wuqisan.ppat.system.service.ConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/system/config/")
@Slf4j
public class ConfigController extends BaseController {
    @Autowired
    ConfigService configService;


    @PostMapping("getConfigList")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("获取所有配置")
    public R<PageInfo<Config>> getConfigList(@RequestBody(required = true) Map<String, String> pageMap) {
        setPage(pageMap);
        PageInfo<Config> configList = configService.getConfigList(pageMap);
        return R.success(configList);
    }

    @PostMapping("updateConfigById")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("修改配置")
    public R<String> updateConfigById(@RequestBody(required = true) Map<String, String> pageMap) {
        try {
            CacheUtils.put(pageMap.get("configId"),pageMap.get("value"));
            configService.updateConfigById(pageMap);
        } catch (Exception e) {
            log.error("修改失败" + e.getMessage());
            return R.error("修改成功" + e.getMessage());
        }
        return R.success("修改成功");
    }
    @GetMapping("queryConfigById/{id}")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("根据ID查询配置信息")
    public R<Config> queryClassById(@PathVariable String id){
        Config classInfo= configService.queryConfigById(id);
        return R.success(classInfo);
    }
}
