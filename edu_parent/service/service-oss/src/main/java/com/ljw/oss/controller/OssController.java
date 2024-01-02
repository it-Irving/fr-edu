package com.ljw.oss.controller;

import com.ljw.commonutils.R;
import com.ljw.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * oss控制器
 *
 * @author Luo
 */
@Api(description = "阿里云文件上传")
@CrossOrigin
@RestController
@RequestMapping("/edu-oss/fileOss")
public class OssController {

    @Autowired
    private OssService ossService;


    /**
     * 图片上传到阿里云
     *
     * @param file 文件
     * @return {@link R}
     */
    @ApiOperation(value = "图片上传")
    @PostMapping("/upload")
    //uploadFileAvatar
    public R upload(@RequestParam("file") MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        return R.ok().message("头像上传成功").data("url", url);
    }
}
