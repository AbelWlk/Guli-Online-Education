package com.wlk.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.entity.Subject;
import com.wlk.service.edu.entity.excel.SubjectData;
import com.wlk.service.edu.listener.SubjectExcelListener;
import com.wlk.service.edu.mapper.SubjectMapper;
import com.wlk.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

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

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            throw new GuliException(ResultCode.ERROR, "Excel读取失败！");
        }
    }
}
