package com.ljw.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 *
 * @author Luo
 */

@Data
public class FirstSubject {

    private String id;

    private String title;


    /**
     * 二级标题
     */
    private List<SecondSubject> children = new ArrayList<>();
}
