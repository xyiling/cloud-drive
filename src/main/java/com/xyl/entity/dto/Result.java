package com.xyl.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.xyl.entity.constants.ResultCode;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private boolean success;
    private Object message;
    private T data;
    private int code;

}

