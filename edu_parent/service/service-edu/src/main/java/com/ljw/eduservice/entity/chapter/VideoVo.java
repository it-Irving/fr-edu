package com.ljw.eduservice.entity.chapter;

import lombok.Data;

/**
 * 视频(小节)
 *
 * @author Luo
 */
@Data
public class VideoVo {
    private String id;

    private String title;

    // 视频id
    private String videoSourceId;
}
