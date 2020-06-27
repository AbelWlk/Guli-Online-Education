package com.wlk.service.edu.controller;


import com.wlk.common.utils.R;
import com.wlk.service.edu.entity.Chapter;
import com.wlk.service.edu.entity.chapter.ChapterVo;
import com.wlk.service.edu.service.ChapterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wlk
 * @since 2020-06-26
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    //课程大纲列表  根据课程id查询
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("items", list);
    }

    @PostMapping("/addChapter")
    public R addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        Boolean flag = chapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

