package com.wlk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.entity.Course;
import com.wlk.service.edu.entity.CourseDescription;
import com.wlk.service.edu.entity.frontvo.CourseFrontVo;
import com.wlk.service.edu.entity.frontvo.CourseWebVo;
import com.wlk.service.edu.entity.vo.CourseInfoVo;
import com.wlk.service.edu.entity.vo.CoursePublishVo;
import com.wlk.service.edu.mapper.CourseMapper;
import com.wlk.service.edu.service.ChapterService;
import com.wlk.service.edu.service.CourseDescriptionService;
import com.wlk.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wlk.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
@Transactional
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private CourseMapper courseMapper;
    @Resource
    private CourseDescriptionService courseDescriptionService;
    @Resource
    private ChapterService chapterService;
    @Resource
    private VideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加课程信息
        //courseInfoVo转换成course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int i = courseMapper.insert(course);
        if (i <= 0) {
            //添加失败
            throw new GuliException(ResultCode.ERROR, "添加课程信息失败！");
        }

        String cid = course.getId();

        //向课程简介表添加课程简介
        CourseDescription description = new CourseDescription();
        //课程描述id就是课程id
        description.setId(cid);
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(description);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //查询课程表
        Course course = courseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        //查询描述表
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {

        //修改课程表
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = courseMapper.updateById(course);
        if (update == 0) {
            throw new GuliException(ResultCode.ERROR, "修改课程信息失败！");
        }

        //修改课程藐视
        CourseDescription description = new CourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String courseId) {
        return courseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        // 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //根据课程id删除课程描述
        courseDescriptionService.removeById(courseId);

        //根据id删除课程
        int i = courseMapper.deleteById(courseId);
        if (i == 0) {
            throw new GuliException(ResultCode.ERROR, "课程删除失败！");
        }
    }

    @Override
    public Map<String, Object> getCourseList(Page<Course> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        courseMapper.selectPage(pageCourse,wrapper);

        List<Course> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();
        boolean hasPrevious = pageCourse.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getCourseBaseInfo(String id) {
        return courseMapper.getCourseBaseInfo(id);
    }
}
