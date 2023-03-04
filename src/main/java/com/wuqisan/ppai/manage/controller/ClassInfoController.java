package com.wuqisan.ppai.manage.controller;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppai.base.bean.R;
import com.wuqisan.ppai.base.controller.BaseController;
import com.wuqisan.ppai.config.aop.authAop.AuthCheck;
import com.wuqisan.ppai.manage.bean.ClassInfo;
import com.wuqisan.ppai.manage.service.ClassService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/manage/class/")
@Slf4j
public class ClassInfoController extends BaseController {
    @Autowired
    ClassService classService;

    @PostMapping("addClass")
    @ApiOperation("添加班级")
    public R<String> addClass(@RequestBody Map<String,String> data){
        log.info("新增班级，班级信息：{}",data.toString());
        try {
            ClassInfo classInfo=new ClassInfo();
            classInfo.setClassId(data.get("grade")+"."+data.get("class"));
            classInfo.setName(data.get("grade")+"年级"+data.get("class")+"班");
            classInfo.setStatus(Integer.valueOf(data.get("status")));
            classService.addClass(classInfo);
        } catch (Exception e) {
            log.error(e.toString());
            return R.error("添加失败,请检查是否已经存在");
        }
        return R.success("添加成功");
    }

    @PostMapping("getClassList")
    @ApiOperation("获取所有班级信息")
    public R<PageInfo<ClassInfo>> getClassList(@RequestBody(required = true) Map<String,String> pageMap){
        setPage(pageMap);
        PageInfo<ClassInfo> classInfoList= classService.getClassListPage(pageMap);
        return R.success(classInfoList);
    }
    @PostMapping("updateClassById")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("修改班级信息")
    public R<String> updateClassById(@RequestBody(required = true) Map<String,String> pageMap){
        try {
            classService.updateClassById(pageMap);
        } catch (Exception e) {
            return R.error("修改成功"+e.getMessage());
        }
        return R.success("修改成功");
    }
    @DeleteMapping("deleteClass/{id}")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("删除班级")
    public R<String> deleteClass(@PathVariable String id){
        try {
            classService.deleteClass(id);
        } catch (Exception e) {
            return R.error("删除失败"+e.getMessage());
        }
        return R.success("删除成功");
    }

    @GetMapping("queryClassById/{id}")
    @AuthCheck("root")//检查是否有Root权限
    @ApiOperation("根据ID查询班级信息")
    public R<ClassInfo> queryClassById(@PathVariable String id){
        ClassInfo classInfo= classService.queryClassById(id);
        return R.success(classInfo);
    }
}
