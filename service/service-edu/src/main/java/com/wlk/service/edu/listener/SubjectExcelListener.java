package com.wlk.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.entity.Subject;
import com.wlk.service.edu.entity.excel.SubjectData;
import com.wlk.service.edu.service.SubjectService;
import com.wlk.service.edu.service.impl.SubjectServiceImpl;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //一行一行去读取excle内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext context) {
        if (subjectData == null) {
            throw new GuliException(ResultCode.ERROR, "Excel数据为空！");
        }
        //一行一行读取 每次一行有两个值，一个一级分类，一个二级分类
        //判断一级分类
        Subject oneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());

        if (oneSubject == null) {//没有相同的一级分类 添加
            oneSubject = new Subject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(oneSubject);
        }
        //判断二级分类
        String pid = oneSubject.getId();
        Subject twoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (twoSubject==null){
            twoSubject = new Subject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(twoSubject);
        }

    }

    //判断一级分类不能重复添加
    private Subject existOneSubject(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        return subjectService.getOne(wrapper);
    }

    //判断二级分类不能重复添加
    private Subject existTwoSubject(SubjectService subjectService, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}