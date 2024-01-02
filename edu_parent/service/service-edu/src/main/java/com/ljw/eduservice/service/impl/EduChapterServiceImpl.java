package com.ljw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.eduservice.entity.EduChapter;
import com.ljw.eduservice.entity.EduVideo;
import com.ljw.eduservice.entity.chapter.ChapterVo;
import com.ljw.eduservice.entity.chapter.VideoVo;
import com.ljw.eduservice.mapper.EduChapterMapper;
import com.ljw.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.eduservice.service.EduVideoService;
import com.ljw.servicebase.exceptionHandler.LjwException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author luo
 * @since 2022-12-13
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    /**
     * 查询章节时，要找小节
     */
    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 获取所有的章节 小节
     *
     * @param courseId 进程id
     * @return {@link List}<{@link ChapterVo}>
     */
    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        // 查询所有章节信息
        QueryWrapper<EduChapter> eduChapterWrapper = new QueryWrapper<>();
        eduChapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(eduChapterWrapper);

        // 查询所有小节信息
        QueryWrapper<EduVideo> eduVideoWrapper = new QueryWrapper<>();
        eduVideoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoWrapper);

        // 最终返回的集合
        List<ChapterVo> finalChapterList = new ArrayList<>();

        // 封装章节 小节信息
        for (EduChapter educhapter : chapterList) {
            //章节vo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(educhapter, chapterVo);

            // 封装章节
            finalChapterList.add(chapterVo);

            // 每次都新建集合
            List<VideoVo> videoVoList = new ArrayList<>();

            for (EduVideo eduVideo : eduVideoList){
                // 封装章节对应的小节
                if(eduVideo.getChapterId().equals(chapterVo.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    // 封装小节
                    videoVoList.add(videoVo);
                }
            }
            // 所有小节封入章节
            chapterVo.setChildren(videoVoList);
        }

        return finalChapterList;
    }

    /**
     * 删除章节( 无小节才能删除 )
     *
     * @param chapterId 章id
     * @return boolean
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        // 通过章节id 查询小节的记录数目
        QueryWrapper<EduVideo> eduVideoWrapper = new QueryWrapper<>();
        eduVideoWrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(eduVideoWrapper);

        if (count > 0){
            throw new LjwException(20001, "删除失败，请先删除小节");
        }else {
            int rs = baseMapper.deleteById(chapterId);
            return rs > 0;
        }
    }

    /**
     * 删除章节进程id
     *
     * @param courseId 进程id
     */
    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(eduChapterQueryWrapper);
    }
}
