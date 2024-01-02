package com.ljw.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节
 *
 * @author Luo
 */
@Data
public class ChapterVo {
    private String id;

    private String title;

    /**
     * 章节包含小节
     */
    private List<VideoVo> children = new ArrayList<>();
}
