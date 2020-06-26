package com.wlk.service.edu.controller;


import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.subject.OneSubject;
import com.wlk.service.edu.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-25
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Resource
    private SubjectService subjectService;

    //添加课程分类
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {

        //得到上传的文件
        subjectService.saveSubject(file, subjectService);

        return R.ok();
    }

    //课程分类列表   树形
    @GetMapping("/getAllSubject")
    public R getAllSubject() {

        //一级分类中本身包含二级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("items",list);
    }

}

