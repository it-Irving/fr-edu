package com.ljw.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ljw.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 短信服务
 *
 * @author Luo
 */
@Service
public class MsmServiceImpl implements MsmService {
    /**
     * 调用阿里云发送短信方法
     *
     * @param codeMap 短信验证码map
     * @param phone   电话
     * @return boolean
     */
    @Override
    public boolean sendMsm(Map<String, Object> codeMap, String phone) {
        if(StringUtils.isEmpty(phone)){
            return false;
        }

        DefaultProfile profile = DefaultProfile.getProfile(
                "default",
                "LTAI5t9ryzW7w9V6YHwCnbTu",
                "badKYwGlU75uG3AbXutLcwKT0AgIpR");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关参数(固定的,不需要修改)
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST); //提交方式
        request.setDomain("dysmsapi.aliyuncs.com"); //发送时要访问阿里云中的哪个地方
        request.setVersion("2017-05-25"); //版本号
        request.setAction("SendSms"); //请求里面的哪个方法


        //设置发送的相关参数
        request.putQueryParameter("PhoneNumbers", phone); //设置要发送的手机号
        request.putQueryParameter("SignName", "阿里云短信测试"); //在阿里云申请的签名名称
        request.putQueryParameter("TemplateCode", "SMS_154950909"); //在阿里云中申请的模板Code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(codeMap)); //验证码数据

        try {
            //最终发送
            CommonResponse response = client.getCommonResponse(request);

            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
