package com.wlk.service.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.Course;
import com.wlk.service.edu.entity.chapter.ChapterVo;
import com.wlk.service.edu.entity.frontvo.CourseFrontVo;
import com.wlk.service.edu.entity.frontvo.CourseWebVo;
import com.wlk.service.edu.service.ChapterService;
import com.wlk.service.edu.service.CourseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/edu/front")
@RestController
public class CourseFrontController {

    @Resource
    private CourseService courseService;
    @Resource
    private ChapterService chapterService;

    @PostMapping("/getCourseList/{page}/{limit}")
    public R getCourseList(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<Course> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseList(pageCourse, courseFrontVo);

        return R.ok().data(map);
    }

    @GetMapping("/getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id) {
        CourseWebVo courseWebVo = courseService.getCourseBaseInfo(id);

        List<ChapterVo> byCourseId = chapterService.getChapterVideoByCourseId(id);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", byCourseId);

    }

}
