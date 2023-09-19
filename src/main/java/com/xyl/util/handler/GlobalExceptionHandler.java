package com.xyl.util.handler;

import com.xyl.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.fail(e.getMessage());
    }
}
