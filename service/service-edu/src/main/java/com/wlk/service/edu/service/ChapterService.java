package com.wlk.service.edu.service;

import com.wlk.service.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wlk.service.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    Boolean deleteChapter(String chapterId);
}
