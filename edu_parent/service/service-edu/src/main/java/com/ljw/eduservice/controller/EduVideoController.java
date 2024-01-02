package com.ljw.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljw.commonutils.R;
import com.ljw.eduservice.client.VodClient;
import com.ljw.eduservice.entity.EduVideo;
import com.ljw.eduservice.service.EduVideoService;
import com.ljw.servicebase.exceptionHandler.LjwException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author luo
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
@Api(description = "小节管理api")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 注入vodClient
     */
    @Autowired
    private VodClient vodClient;


    @ApiOperation(value = "增-添加小节")
    @PostMapping("/addVideo")
    // addVideo
    public R addVideo(@RequestBody EduVideo video){
        eduVideoService.save(video);
        return R.ok();
    }

    @ApiOperation(value = "查-查询小节")
    @GetMapping("/getVideo/{VideoId}")
    public R getVideo(@PathVariable String VideoId){
        EduVideo eduVideo = eduVideoService.getById(VideoId);
        return R.ok().data("video", eduVideo);
    }

    @ApiOperation(value = "改-修改小节")
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo video){
        System.out.println(video);
        eduVideoService.updateById(video);
        return R.ok();
    }

    /**
     * 删除视频
     *  服务调用
     * @param VideoId 视频id
     * @return {@link R}
     */
    @ApiOperation(value = "删除-删除小节（全部删除）")
    @DeleteMapping("/{VideoId}")
    public R deleteVideo(@PathVariable String VideoId){
        // 通过小节id，查到视频资源id
        EduVideo eduVideo = eduVideoService.getById(VideoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        //TODO
        // 通过视频id，远程调用removeAlyVideo
        if (!StringUtils.isEmpty(videoSourceId)){
//            vodClient.removeAlyVideo(videoSourceId);
            R rs = vodClient.removeAlyVideo(videoSourceId);
            if (rs.getCode() == 20001){
                throw new LjwException(20001, "删除视频失败，熔断器。。。。。。");
            }
        }

        boolean flag = eduVideoService.removeById(VideoId);
        if (!flag){
            throw new LjwException(20001, "删除失败");
        }else {
            return R.ok();
        }
    }
}

