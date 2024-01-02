package com.ljw.eduservice.service;

import com.ljw.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ljw.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author luo
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 获取所有的章节 小节
     *
     * @param courseId 进程id
     * @return {@link List}<{@link ChapterVo}>
     */
    List<ChapterVo> getChapterVideo(String courseId);

    /**
     * 删除章节( 无小节才能删除 )
     *
     * @param chapterId 章id
     * @return boolean
     */
    boolean deleteChapter(String chapterId);

    /**
     * 删除章节进程id
     *
     * @param courseId 进程id
     */
    void deleteChapterByCourseId(String courseId);
}
