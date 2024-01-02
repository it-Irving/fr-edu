package com.ljw.eduorder.client;

import com.ljw.commonutils.orderVo.CourseWebVoOrder;
import com.ljw.eduorder.client.impl.EduClientFileDegradeFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 调用edu
 *
 * @author Luo
 */
@FeignClient(name = "service-edu", fallback = EduClientFileDegradeFeignClient.class) // 用于指定从哪个服务中调用功能 ,名称与被调用的服务名保持一致
@Component
public interface EduClient {

    /**
     * 通过id获取课程，远程调用
     *
     * @param courseId 进程id
     * @return {@link CourseWebVoOrder}
     */
    @ApiOperation(value = "通过id获取课程，远程调用")
    @GetMapping("/eduservice/courseFront/getEduCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getEduCourseInfoOrder(@PathVariable String courseId);
}
