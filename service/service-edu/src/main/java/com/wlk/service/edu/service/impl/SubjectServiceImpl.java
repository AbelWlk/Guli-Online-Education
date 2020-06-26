package com.wlk.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.entity.Subject;
import com.wlk.service.edu.entity.excel.SubjectData;
import com.wlk.service.edu.entity.subject.OneSubject;
import com.wlk.service.edu.entity.subject.TwoSubject;
import com.wlk.service.edu.listener.SubjectExcelListener;
import com.wlk.service.edu.mapper.SubjectMapper;
import com.wlk.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author wlk
 * @since 2020-06-25
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            throw new GuliException(ResultCode.ERROR, "Excel读取失败！");
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {

        //最终返回的结果
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //查询出所有的一级分类 parent_id=0
        QueryWrapper<Subject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<Subject> oneSubjects = subjectMapper.selectList(wrapperOne);

        //查询所有的二级分类
        QueryWrapper<Subject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<Subject> twoSubjects = subjectMapper.selectList(wrapperTwo);

        //封装
        //处理一级分类
        Map<String, OneSubject> map = new HashMap<>();

        for (Subject oneSubject : oneSubjects) {
            OneSubject one = new OneSubject();
            one.setId(oneSubject.getId());
            one.setTitle(oneSubject.getTitle());
            finalSubjectList.add(one);
            map.put(one.getId(), one);
        }

        //处理二级分类
        for (Subject twoSubject : twoSubjects) {
            TwoSubject two = new TwoSubject();
            two.setId(twoSubject.getId());
            two.setTitle(twoSubject.getTitle());
            OneSubject parent = map.get(twoSubject.getParentId());
            parent.getChildren().add(two);
        }


        return finalSubjectList;
    }
}
