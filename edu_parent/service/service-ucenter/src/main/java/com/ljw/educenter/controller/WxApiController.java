package com.ljw.educenter.controller;

import com.google.gson.Gson;
import com.ljw.commonutils.JwtUtils;
import com.ljw.educenter.entity.UcenterMember;
import com.ljw.educenter.service.UcenterMemberService;
import com.ljw.educenter.utils.ConstantWxUtils;
import com.ljw.educenter.utils.HttpClientUtils;
import com.ljw.servicebase.exceptionHandler.LjwException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信登录
 *
 * @author Luo
 */
@CrossOrigin
@Controller //@RestController注解,方法返回字符串u时就会直接将字符串写到浏览器中
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
    @Autowired
    private UcenterMemberService ucenterMemberService;


    //生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        //1.url中的%s就相当于问号(?),代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //2.对redirect_url进行URLEncode编码
        String redirect_url = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirect_url = URLEncoder.encode(redirect_url, "utf-8");
        } catch(Exception e) {

        }
        //3.给baseUrl中的占位符(%s)赋值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_REDIRECT_URL,
                "atguigu" //老师说了state目前没啥用,只是给他传个值atguigu
        ); //方法的返回值就是完整的带有参数的url地址
        //4.重定向到请求微信地址里面
        return "redirect:" + url;
    }

    @GetMapping("callback")
    public String callback(String code, String state) {
        System.out.println("code: " + code);
        System.out.println("state: "  + state + "\n\n");
//        return "redirect:http://localhost:3000";

        try {
            //1.拿着code(这是一个临时票据,类似于验证码)请求微信给的固定地址
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //1.1给地址拼接三个参数:id、秘钥、code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);
            //1.2使用httpclient请求拼接好的地址,得到openid和access_token
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //输出accessTokenInfo看一下这个字符串长什么样,以便分析后面的业务逻辑
            System.out.println(accessTokenInfo + "\n\n");



            //从json字符串中获取openid和access_token
            //使用json工具把accessTokenInfo字符串转换为map集合,根据map中的key获取对应的值
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            // 通过微信id查询会员信息
            UcenterMember ucenterMember = ucenterMemberService.getMemberByOpenId(openid);

            // 没有就进行添加（注册）
            if (ucenterMember == null) {
                //2.拿着access_token和openid请求微信给的固定地址
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //2.1给地址拼接两个参数:access_token、openid
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                //2.2使用httpclient请求拼接好的地址
                String userInfo = HttpClientUtils.get(userInfoUrl);
                //输出userInfo看一下这个字符串长什么样,以便分析后面的业务逻辑
                System.out.println(userInfo + "\n\n");

                // 3.获取扫码人的信息
                // 转换json字符串为map集合
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String headimgurl = (String) userInfoMap.get("headimgurl");//头像
                String nickname = (String) userInfoMap.get("nickname");//用户名

                //添加用户信息
                ucenterMember = new UcenterMember();
                ucenterMember.setOpenid(openid);
                ucenterMember.setNickname(nickname);
                ucenterMember.setAvatar(headimgurl);
                ucenterMemberService.save(ucenterMember);
            }

            //返回到首页面
            // 生成token
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            throw new LjwException(20001, "登录失败");
        }

    }

}
