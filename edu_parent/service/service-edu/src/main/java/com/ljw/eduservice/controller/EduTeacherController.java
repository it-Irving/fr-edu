package com.ljw.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ljw.commonutils.R;
import com.ljw.eduservice.entity.EduTeacher;
import com.ljw.eduservice.entity.vo.TeacherQuery;
import com.ljw.eduservice.service.EduTeacherService;
import com.ljw.servicebase.exceptionHandler.LjwException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation.ANONYMOUS.required;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author luo
 */
@RestController //注解、处理json请求体
@CrossOrigin    //解决跨域问题
@RequestMapping("/eduservice/edu-teacher")
/**
 * EnableSwagger2注解
 定义在类上： @Api
 定义在方法上： @ApiOperation
 定义在参数上： @ApiParam
 * */
@Api(description="讲师管理api")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有
     *
     * @return {@link List}<{@link EduTeacher}>
     */
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("pageTeacherCondition")
    public R findAll(){
        List<EduTeacher> eduTeacherList = eduTeacherService.list(null);
        return R.ok().data("items", eduTeacherList);
    }

    /**
     * 逻辑删除教师
     *
     * @param id id
     * @return boolean
     */
    @ApiOperation(value = "逻辑删除教师")
    @DeleteMapping("{id}")
    public R deleteTeacherById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 页面列表
     *
     * @return {@link R}
     */
    @ApiOperation(value = "讲师列表分页查询")
    @GetMapping("/pageList/{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页", required = true) @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable Long limit){

        //1.创建page对象
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);

        //2.使用分页查询方法，分页查询的方法会封装到 page对象中（pageParam）
        eduTeacherService.page(pageParam, null);

        //3.获取查询数据 (获取所有记录)
        List<EduTeacher> records = pageParam.getRecords();
        //获取总记录数
        long total = pageParam.getTotal();

        //4.封装数据，返回结果集
        return R.ok().data("total",total).data("rows", records);
    }



    @ApiOperation(value = "查-讲师条件查询 带分页")
    @PostMapping("/pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页大小", required = true) @PathVariable Long limit,
                                  @ApiParam(name = "teacherQuery", value = "查询讲师的条件", required = false) @RequestBody(required = false) TeacherQuery teacherQuery){


        //1.创建page对象
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);

        //2.查询数据
        eduTeacherService.pageTeacherConditionQuery(pageParam, teacherQuery);

        //3.获取总记录
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        //4.响应数据
        return R.ok().data("rows", records).data("total", total);
    }

    @ApiOperation(value = "新增讲师接口")
    @PostMapping("/saveTeacher")
    public R saveTeacher(@ApiParam(name = "eduTeacher", value = "讲师实体") @RequestBody EduTeacher eduTeacher){

        //保存讲师信息
        boolean flag = eduTeacherService.save(eduTeacher);

        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询,主要用于回显")
    @GetMapping("/getTeacherById/{id}")
    public R getTeacherById(@ApiParam(name = "id", value = "讲师id") @PathVariable String id){

        //保存讲师信息
        EduTeacher teacher = eduTeacherService.getById(id);

        if(teacher != null){
            return R.ok().data("item", teacher);
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@ApiParam(name = "eduTeacher", value = "讲师实体") @RequestBody EduTeacher eduTeacher){

        //保存讲师信息
        boolean flag = eduTeacherService.updateById(eduTeacher);

        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "测试全局异常")
    @GetMapping("/aaa")
    public R getTeacherById(){
        try {
            int a = 1/0;
        } catch (Exception e) {
            throw new LjwException(2001, "自定义异常信息");
        }
        return R.ok();
    }

}