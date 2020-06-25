package com.wlk.service.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.common.utils.R;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.entity.Teacher;
import com.wlk.service.edu.entity.vo.TeacherQuery;
import com.wlk.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-24
 */
@CrossOrigin
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
public class TeacherController {

    //注入service
    @Autowired
    private TeacherService teacherService;

    //查询讲师所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R findAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //根据id逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/{id}")
    public R removeById(@ApiParam(name = "id", value = "讲师ID", required = true)
                        @PathVariable("id") String id) {
        if (teacherService.removeById(id)) {
            return R.ok();
        } else
            return R.error();
    }

    //分页查询讲师
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        //创建page对象
        Page<Teacher> teacherPage = new Page<>(current, limit);
        //调用方法实现分页
        //方法底层自动封装
        teacherService.page(teacherPage, null);

        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();

        return R.ok().data("total", total).data("items", records);
    }

    //4 条件查询带分页的方法
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<Teacher> pageTeacher = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        // 多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(pageTeacher, wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<Teacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping("/addTeacher")
    public R save(@RequestBody Teacher teacher) {
        boolean flag = teacherService.save(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/{id}")
    public R getById(@PathVariable String id) {
        try {
            //int i=10/0;
        } catch (Exception e) {
            //执行自定义异常
            throw new GuliException(20001, "执行GuliException！！！");
        }
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("/updateTeacher")
    public R updateById(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return R.ok();
    }

}

