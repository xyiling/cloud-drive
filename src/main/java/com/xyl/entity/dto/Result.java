package com.xyl.entity.dto;

import com.xyl.entity.constants.ResultCode;
import lombok.Data;

@Data
public class Result<T> {

    private Integer code;
    private String msg;
    private T data;

    public static Result ok(String msg) {
        Result result = new Result<>();
        result.setCode(ResultCode.OK);
        result.setData(null);
        result.setMsg(msg);
        return result;
    }

    public static Result ok(Object data, String msg) {
        Result result = new Result<>();
        result.setCode(ResultCode.OK);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result<>();
        result.setCode(ResultCode.FAIL);
        result.setData(null);
        result.setMsg(msg);
        return result;
    }

    public static Result JudgeBooleanValue(Boolean flag) {
        return flag ? Result.ok("操作成功") : Result.fail("操作失败");
    }
}
