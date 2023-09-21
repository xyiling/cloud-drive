package com.xyl.util;

import com.xyl.entity.dto.Result;

public class ResultUtil {

    public static Result ok(Object data) {
        Result result = new Result();
        result.setOK(data);
        result.setMessage("操作成功");
        return result;
    }

    public static Result fail(String msg) {
        Result result = new Result();
        result.setMessage(msg);
        return result;
    }

    public static Result judgeBooleanValue(Boolean flag) {
        return flag ? ok(null) : fail("操作失败");
    }
}
