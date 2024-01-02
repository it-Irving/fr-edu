package com.ljw.eduservice.controller;


import com.ljw.commonutils.R;
import com.ljw.eduservice.entity.EduChapter;
import com.ljw.eduservice.entity.chapter.ChapterVo;
import com.ljw.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author luo
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-chapter")
@Api(description = "章节管理api")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    @ApiOperation(value = "查-通过课程id，获取所有章节小节")
    public R getChapterVideo(@PathVariable String courseId){

        // 获取所有的章节 小节
        List<ChapterVo> allChapterVideo = eduChapterService.getChapterVideo(courseId);

        return R.ok().data("allChapterVideo", allChapterVideo);
    }

    // 章节crud
    @ApiOperation(value = "增-章节添加")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @ApiOperation(value = "查-章节查询")
    @GetMapping("getChapterById/{chapterId}")
    public R getChapter(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    @ApiOperation(value = "改-章节修改")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation(value = "删-删除章节")
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

