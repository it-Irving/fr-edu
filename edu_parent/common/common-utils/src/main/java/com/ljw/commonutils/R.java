package com.ljw.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * r
 * 处理统一结果集
 *
 * @author Luo
 */
@Data
public class R {

    /**
     * @ApiModelProperty()注解用于方法、 字段，表示对model属性的说明或者数据操作更改
     * <p>
     * value：字段说明，
     * name：重写属性名字，
     * dataType：重写属性类型，
     * required：是否必须，默认false，
     * example：举例，
     * hidden：隐藏
     */

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();



    /**
     * 私有化构造方法
     */
    private R() {
    }

    /**
     * 私有化构造方法（内部创建对象）
     */
    public static R ok() {
        R r = new R();
        //补充是否成功、状态码、信息
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        //补充是否成功、状态码、信息
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    //链式编程
    public R success(boolean success){
        this.setSuccess(success);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
}