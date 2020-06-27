package com.wlk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wlk.common.utils.ResultCode;
import com.wlk.service.base.exceptionhandler.GuliException;
import com.wlk.service.edu.entity.Chapter;
import com.wlk.service.edu.entity.Video;
import com.wlk.service.edu.entity.chapter.ChapterVo;
import com.wlk.service.edu.entity.chapter.VideoVo;
import com.wlk.service.edu.mapper.ChapterMapper;
import com.wlk.service.edu.mapper.VideoMapper;
import com.wlk.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;
    @Resource
    private VideoMapper videoMapper;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //最终封装的数据
        List<ChapterVo> finalChapterVoList = new ArrayList<>();

        //查询所有章节 根据课程id
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<Chapter> chapterList = chapterMapper.selectList(chapterWrapper);

        //查询小节
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<Video> videoList = videoMapper.selectList(videoWrapper);

        //遍历封装章节

        Map<String, ChapterVo> map = new HashMap<>();

        for (Chapter chapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(chapter.getId());
            chapterVo.setTitle(chapter.getTitle());
            finalChapterVoList.add(chapterVo);
            map.put(chapterVo.getId(), chapterVo);
        }

        //遍历封装小节
        for (Video video : videoList) {
            VideoVo videoVo = new VideoVo();
            videoVo.setId(video.getId());
            videoVo.setTitle(video.getTitle());
            ChapterVo parent = map.get(video.getChapterId());
            parent.getChildren().add(videoVo);
        }

        return finalChapterVoList;
    }

    //删除的同时删除相关的小节
    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        Integer count = videoMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(ResultCode.ERROR, "小节不为空！");
        } else {
            int result = chapterMapper.deleteById(chapterId);
            return result > 0;
        }
    }
}
