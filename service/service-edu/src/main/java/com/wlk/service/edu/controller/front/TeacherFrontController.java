package com.wlk.service.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.Course;
import com.wlk.service.edu.entity.Teacher;
import com.wlk.service.edu.service.CourseService;
import com.wlk.service.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/edu/front")
@RestController
public class TeacherFrontController {

    @Resource
    private TeacherService teacherService;
    @Resource
    private CourseService courseService;

    @PostMapping("/getTeacher/{page}/{limit}")
    public R getFrontTeacher(@PathVariable Long page, @PathVariable Long limit) {
        Page<Teacher> pageTeacher = new Page<>(page, limit);
        Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);

        return R.ok().data(map);
    }

    @GetMapping("/getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        //根据讲师id查询将是信息
        Teacher teacher = teacherService.getById(id);

        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<Course> courseList = courseService.list(wrapper);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }


}
