package com.ljw.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteMezzaninesRequest;
import com.aliyuncs.vod.model.v20170321.DeleteMezzaninesResponse;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.ljw.servicebase.exceptionHandler.LjwException;
import com.ljw.vod.service.VodService;
import com.ljw.vod.utils.ConstantVodUtils;
import com.ljw.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.ljw.vod.utils.InitVodClient.initVodClient;

/**
 * 视频点播服务impl
 *
 * @author Luo
 * @date 2023/01/10
 */
@Service
public class VodServiceImpl implements VodService {
    /**
     * 上传阿里视频
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String uploadAlyVideo(MultipartFile file) {
        try {
            String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;
            //上传的文件的原始名称(如:xxxxx.mp4)
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();

            /**
             * 流式上传接口
             *
             * @param accessKeyId
             * @param accessKeySecret
             * @param title
             * @param fileName
             * @param inputStream
             */
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId,
                    accessKeySecret,
                    title,
                    fileName,
                    inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            // 请求视频点播服务的请求ID
            String videoId = null;

            if (response.isSuccess()) {
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        /*
        try {
            //上传的文件的原始名称(如:01.mp4)
            String fileName = file.getOriginalFilename();
            //上传后在阿里云显示的名称(带不带后缀都行,我这里没带)
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //上传的文件的输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET,
                    title,
                    fileName,
                    inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }

            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
*/
    }

    /**
     * 删除阿里云视频
     *
     * @param id id
     */
    @Override
    public void removeAlyVideo(String id) {
        try {
            // 1. 初始化
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            // 请求、响应
            DeleteVideoResponse response = new DeleteVideoResponse();
            DeleteVideoRequest request = new DeleteVideoRequest();

            //  向request对象里面设置删除的视频id
            request.setVideoIds(id);

            // 调用初始化对象里面的方法,实现删除
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new LjwException(20001, "删除视频失败");
        }
    }

    /**
     * 删除批处理
     *
     * @param videoIdList 视频id列表
     */
    @Override
    public void deleteBatch(List<String> videoIdList) {
        try {
            // 1.初始化
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            // 2.
            DeleteVideoResponse response = new DeleteVideoResponse();
            DeleteVideoRequest request = new DeleteVideoRequest();

            //支持传入多个视频ID，多个用逗号分隔
            //需要先将videoIdList集合中的id遍历为1,2,3的形式
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            request.setVideoIds(videoIds);
            response = client.getAcsResponse(request);

        } catch (ClientException e) {
            e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new LjwException(20001,"删除视频失败");
        }

    }


    /**
     * 测试批量删除
     *
     * @param args arg游戏
     */
    public static void main(String[] args) {
        List<String> videoIdList = new ArrayList<>();
        videoIdList.add("54d43c9091ca71edaf2a6632b68f0102");
        videoIdList.add("45d45cc091ca71edbb680764a0fd0102");
        try {
            // 1.初始化
            DefaultAcsClient client = initVodClient("LTAI5t9ryzW7w9V6YHwCnbTu", "badKYwGlU75uG3AbXutLcwKT0AgIpR");

//            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            // 2.
            DeleteVideoResponse response = new DeleteVideoResponse();
            DeleteVideoRequest request = new DeleteVideoRequest();

            //支持传入多个视频ID，多个用逗号分隔
            //需要先将videoIdList集合中的id遍历为1,2,3的形式
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            request.setVideoIds(videoIds);
            response = client.getAcsResponse(request);

        } catch (ClientException e) {
            e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new LjwException(20001,"删除视频失败");
        }

    }

}

