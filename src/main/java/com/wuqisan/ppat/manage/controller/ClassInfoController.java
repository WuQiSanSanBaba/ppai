package com.wuqisan.ppat.manage.controller;

import com.github.pagehelper.PageInfo;
import com.wuqisan.ppat.base.bean.R;
import com.wuqisan.ppat.base.controller.BaseController;
import com.wuqisan.ppat.config.aop.authAop.AuthCheck;
import com.wuqisan.ppat.manage.bean.GradeClass;
import com.wuqisan.ppat.manage.service.ClassService;
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
            GradeClass gradeClass =new GradeClass();
            gradeClass.setClassId(data.get("grade")+"."+data.get("class"));
            gradeClass.setName(data.get("grade")+"年级"+data.get("class")+"班");
            gradeClass.setStatus(Integer.valueOf(data.get("status")));
            classService.addClass(gradeClass);
        } catch (Exception e) {
            log.error(e.toString());
            return R.error("添加失败,请检查是否已经存在");
        }
        return R.success("添加成功");
    }

    @PostMapping("getClassList")
    @ApiOperation("获取所有班级信息")
    public R<PageInfo<GradeClass>> getClassList(@RequestBody(required = true) Map<String,String> pageMap){
        setPage(pageMap);
        PageInfo<GradeClass> classInfoList= classService.getClassListPage(pageMap);
        return R.success(classInfoList);
    }
    @PostMapping("updateClassById")
    @AuthCheck("ppat")//检查是否有Root权限
    @ApiOperation("修改班级信息")
    public R<String> updateClassById(@RequestBody(required = true) Map<String,String> pageMap){
        try {
            classService.updateClassById(pageMap);
        } catch (Exception e) {
            log.error("修改失败"+e.getMessage());
            return R.error("修改失败"+e.getMessage());
        }
        return R.success("修改成功");
    }
    @DeleteMapping("deleteClass/{id}")
    @AuthCheck("ppat")//检查是否有Root权限
    @ApiOperation("删除班级")
    public R<String> deleteClass(@PathVariable String id){
        try {
            classService.deleteClass(id);
        } catch (Exception e) {
            log.error("删除失败"+e.getMessage());
            return R.error("删除失败"+e.getMessage());
        }
        return R.success("删除成功");
    }

    @GetMapping("queryClassById/{id}")
    @AuthCheck("ppat")//检查是否有Root权限
    @ApiOperation("根据ID查询班级信息")
    public R<GradeClass> queryClassById(@PathVariable String id){
        GradeClass gradeClass = classService.queryClassById(id);
        return R.success(gradeClass);
    }
}
