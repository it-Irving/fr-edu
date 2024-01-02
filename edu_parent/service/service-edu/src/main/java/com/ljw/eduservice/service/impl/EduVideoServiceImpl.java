package com.ljw.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.commonutils.R;
import com.ljw.eduservice.client.VodClient;
import com.ljw.eduservice.entity.EduVideo;
import com.ljw.eduservice.mapper.EduVideoMapper;
import com.ljw.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljw.servicebase.exceptionHandler.LjwException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author luo
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    /**
     * 删除视频课程简讯id
     *
     * @param courseId 进程id
     */
    @Override
    public void deleteVideoByCourseId(String courseId) {
        // TODO 删除小节前先删除小节下的所有视频
        // 通过课程id 查出所有 小节下的视频id
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(videoQueryWrapper);

        // 封装视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (EduVideo video : videoList){
            String videoSourceId = video.getVideoSourceId();

            if (!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }

        // 远程调用 service-vod的删除方法
        // 如果有视频id
        if (videoList.size() > 0){
            R rs = vodClient.deleteBatch(videoSourceIdList);
            if (rs.getCode() == 20001){
                throw new LjwException(20001, "删除单个视频失败，熔断器。。。。。");
            }
        }

        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        baseMapper.delete(eduVideoQueryWrapper);
    }
}
