package com.ljw.vodTest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import org.junit.Test;

import java.util.List;

import static com.ljw.vodTest.InitObject.initVodClient;

/**
 * 测试视频点播
 *
 * @author Luo
 */
public class TestVod {
    /**
     * 根据id，获取播放地址、视频名称
     *
     * @throws ClientException 客户端异常
     */
    @Test
    public void test1() throws ClientException {
        // 1. 初始化对象，调用服务端接口需要使用AccessKey完成身份验证
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t9ryzW7w9V6YHwCnbTu",
                "badKYwGlU75uG3AbXutLcwKT0AgIpR");

        //2.创建获取视频地址的request和response
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        GetPlayInfoRequest request = new GetPlayInfoRequest();

        //3.向request对象里面设置视频id(必须是没有加密的视频id)
        request.setVideoId("c64d6b4090bb71ed8a676732b68e0102");

        //4.调用初始化对象里面的方法,获取视频信息
        response  = client.getAcsResponse(request);

        //5.从获取到的视频信息中取两个信息:播放地址和视频名称
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();

        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }

        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    /**
     * 根据视频id获取视频播放凭证
     *
     * @throws ClientException 客户端异常
     */
    @Test
    public void test2() throws ClientException {
        // 1. 初始化对象，调用服务端接口需要使用AccessKey完成身份验证
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t9ryzW7w9V6YHwCnbTu",
                "badKYwGlU75uG3AbXutLcwKT0AgIpR");

        // 2.创建获取视频凭证的response、request
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

        // 3.设置视频id（加密、不加密都可以）
        request.setVideoId("e46ee40091c971edbf990764a0ec0102");
//        request.setVideoId("52f90c17ab8541169c4464c374200a79");
        response = client.getAcsResponse(request);

        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
    }


}
