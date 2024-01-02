package com.ljw.educenter.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.commonutils.JwtUtils;
import com.ljw.commonutils.R;
import com.ljw.commonutils.vo.UcenterMemberVo;
import com.ljw.educenter.entity.RegisterVo;
import com.ljw.educenter.entity.UcenterMember;
import com.ljw.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author luo
 */
@CrossOrigin    // 跨域注解
@RestController // 响应json数据
@RequestMapping("/educenter/ucenter-member")    // 请求映射
@Api(description="用户管理api")
public class UcenterMemberController {

    @Autowired
    // 自动注入service服务
    private UcenterMemberService ucenterMemberService;

    /**
     * 登录 MD5、Jwt
     *
     * @param ucenterMember ucenter成员
     * @return {@link R}
     */
    @ApiOperation(value = "登录") // Swagger注解对方法描述
    @PostMapping("login")   // post请求
    public R login(@RequestBody UcenterMember ucenterMember){
        // 调用service方法，使用jwt生成token
        String token = ucenterMemberService.login(ucenterMember);
//        System.out.println("---------------------后台生成的token--------------" + token);
        // 返回响应数据
        return R.ok().data("token", token);
    }

    /**
     * 注册功能，这里需要校验redis验证码
     *
     * @param registerVo 注册需要的参数
     * @return {@link R}
     */
    @ApiOperation(value = "注册功能")   // Swagger注解对方法描述
    @PostMapping("register")    // post请求
    public R register(@RequestBody RegisterVo registerVo){
        // 调用service方法进行注册
        //ucenterMemberService.register(registerVo);

        ucenterMemberService.register2(registerVo);
        // 返回响应数据
        return R.ok();
    }

    /**
     * 获取成员信息
     *
     * @param request 请求
     * @return {@link R}
     */
    @ApiOperation(value = "根信据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        // 根据token获取会员id
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库,根据用户id得到用户信息
        UcenterMember ucenterMember = ucenterMemberService.getById(memberIdByJwtToken);

        return R.ok().data("userInfo", ucenterMember);
    }

    /**
     * 通过会员id获取成员信息
     *
     * @param memberId 成员身份
     * @return {@link UcenterMemberVo}
     */
    @ApiOperation(value = "根信据会员id获取用户信息")
    @PostMapping("getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable String memberId){
        //查询数据库,根据用户id得到用户信息
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        // 服务调用，创建公共类返回
        UcenterMemberVo ucenterMemberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(ucenterMember, ucenterMemberVo);

        return ucenterMemberVo;
    }

    @ApiOperation(value = "统计注册人数（远程调用）")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        // 查询用户表条目,写sql处理日期2023-01-01
        Integer registerNum = ucenterMemberService.countRegisterByDay(day);

        return R.ok().data("register_num", registerNum);
    }


    // ===============================================用户管理===============================================
    @ApiOperation(value = "查询所有的用户")
    @GetMapping("getAllMember")
    public R getAllMember(){
        QueryWrapper<UcenterMember> memberqueryWrapper = new QueryWrapper<UcenterMember>();
//        memberqueryWrapper.orderByDesc("id");
        List<UcenterMember> list = ucenterMemberService.list(memberqueryWrapper);
        return R.ok().data("list", list);
    }


    @ApiOperation(value = "查询所有的用户")
    @GetMapping("getAllMemberPage/{page}/{limit}")
    public R getAllMemberPage(@ApiParam(name = "page", value = "当前页", required = true) @PathVariable Long page,
                              @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable Long limit ) {
        Page<UcenterMember> ucenterMemberPage = new Page(page, limit);

        QueryWrapper<UcenterMember> memberqueryWrapper = new QueryWrapper<UcenterMember>();
        memberqueryWrapper.orderByDesc("id");

        //2.使用分页查询方法，分页查询的方法会封装到 page对象中（pageParam）
        ucenterMemberService.page(ucenterMemberPage, memberqueryWrapper);

        //3.获取查询数据 (获取所有记录)
        List<UcenterMember> records = ucenterMemberPage.getRecords();
        //获取总记录数
        long total = ucenterMemberPage.getTotal();

        //4.封装数据，返回结果集
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("deleteMember/{memberId}")
    public R deleteMember(@ApiParam(name = "page", value = "用户id", required = true) @PathVariable String memberId) {
        boolean flag = ucenterMemberService.removeById(memberId);

        return R.ok();
    }
}

