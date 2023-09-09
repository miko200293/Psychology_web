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
    public static Result successByKeyValue(String key, Object value) {
        Result result = new Result();
        result.code = 200;
        result.message = "Success";
        result.data.put(key, value);
        return result;
    }

    public static Result errorByCodeMessage(int code, String message) {
        Result result = new Result();
        result.code = code;
        result.message = message;
        return result;
    }
    public static Result successString(String message) {
        Result result = new Result();
        result.code = 200;
        result.message = message;
        return result;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
