package com.wlk.service.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.Course;
import com.wlk.service.edu.entity.Teacher;
import com.wlk.service.edu.service.CourseService;
import com.wlk.service.edu.service.TeacherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RequestMapping("/edu/indexfront")
@RestController
public class IndexFrontController {

    @Resource
    private CourseService courseService;
    @Resource
    private TeacherService teacherService;

    //查询前8条热门课程 前四名教师
    @GetMapping("/index")
    public R index(){
        QueryWrapper<Course> courseQueryWrapper=new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<Course> courseList = courseService.list(courseQueryWrapper);

        QueryWrapper<Teacher> teacherQueryWrapper=new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id");
        teacherQueryWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherQueryWrapper);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }


}
