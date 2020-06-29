package com.wlk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlk.service.edu.entity.Teacher;
import com.wlk.service.edu.mapper.TeacherMapper;
import com.wlk.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-24
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> pageTeacher) {

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        teacherMapper.selectPage(pageTeacher, wrapper);

        List<Teacher> teacherList = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long total = pageTeacher.getTotal();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String, Object> map = new HashMap<>();

        map.put("items", teacherList);
        map.put("current", current);
        map.put("total", total);
        map.put("pages", pages);
        map.put("size", size);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);


        return map;
    }
}
