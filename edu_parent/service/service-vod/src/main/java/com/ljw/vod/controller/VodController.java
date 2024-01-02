package com.ljw.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.ljw.commonutils.R;
import com.ljw.servicebase.exceptionHandler.LjwException;
import com.ljw.vod.service.VodService;
import com.ljw.vod.utils.ConstantVodUtils;
import com.ljw.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 视频点播控制器
 *
 * @author Luo
 */
@RequestMapping("/eduvod/video") // 请求映射
@RestController // 响应json数据
@CrossOrigin // 跨域注解
@Api(description = "阿里云视频点播") // Swagger注解对类描述
public class VodController {
    @Autowired // 注入service
    private VodService vodService;

    /**
     * 上传阿里视频
     *
     * @param file 文件
     * @return {@link R}
     */
    @ApiOperation(value = "上传阿里视频")
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){
        // 返回上传的视频id
        String videoId = vodService.uploadAlyVideo(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 删除阿里视频
     *
     * @param id id
     * @return {@link R}
     */
    @ApiOperation(value = "删除阿里视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id){
        vodService.removeAlyVideo(id);
        return R.ok();
    }

    /**
     * 批量删除阿里视频
     *
     * @param videoIdList 视频id列表
     * @return {@link R}
     */
    @ApiOperation(value = "批量删除阿里视频")
    @DeleteMapping("deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.deleteBatch(videoIdList);
        return R.ok();
    }

    //-----------------------------------------------------------

    /**
     * 通过阿里云视频id获取阿里云播放凭证
     *
     * @param id id
     * @return {@link R}
     */
    @ApiOperation(value = "通过阿里云视频id获取阿里云播放凭证") // Swagger注解对方法描述
    @GetMapping("getPlayAuth/{id}") // get请求
    public R getPlayAuth(@PathVariable String id) {
        try {
            // 1. 初始化对象，调用阿里云服务端接口需要使用AccessKey进行身份验证
            DefaultAcsClient client = InitVodClient.initVodClient(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET);

            // 2.1 创建获取视频凭证的response
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            // 2.2 创建获取视频凭证的request
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            // 3.设置阿里云视频的id（使用阿里云凭证播放加密、不加密的视频都可以）
            request.setVideoId(id);
            //request.setVideoId("52f90c17ab8541169c4464c374200a79");
            response = client.getAcsResponse(request);  // 获取响应信息
            String playAuth = response.getPlayAuth();   // 从响应中取出阿里云播放凭证

            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n"); // 输出播放凭证
            //VideoMeta信息
            //System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
            return R.ok().data("playAuth", playAuth);   //返回阿里云播放凭证
        } catch (ClientException e) {
            throw new LjwException(20001, "当前阿里云凭证获取失败！");  // 获取失败，抛出异常信息
        }
    }

}
