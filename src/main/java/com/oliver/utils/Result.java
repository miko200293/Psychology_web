package com.oliver.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Data
@Component
public class Result {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data=new HashMap<>();

    private Result(){}
    //set Data 方法
    private void setData(String message, Object obj) {
    }

    public static Result ok(){
        Result r =new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }
    public static Result error(){
        Result r =new Result();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }
    public static Result successObeject(String message,Object obj){
        Result r =new Result();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        r.setData(message,obj);
        return r;
    }




}
