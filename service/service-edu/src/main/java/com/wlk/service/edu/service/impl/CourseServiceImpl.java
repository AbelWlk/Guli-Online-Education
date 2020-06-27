package com.wlk.service.edu.service.impl;

import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.entity.Course;
import com.wlk.service.edu.entity.CourseDescription;
import com.wlk.service.edu.entity.vo.CourseInfoVo;
import com.wlk.service.edu.entity.vo.CoursePublishVo;
import com.wlk.service.edu.mapper.CourseMapper;
import com.wlk.service.edu.service.CourseDescriptionService;
import com.wlk.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
}
