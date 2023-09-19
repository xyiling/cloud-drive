package com.xyl.controller;

import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.dto.response.UserResultDto;
import com.xyl.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api("用户表-用户管理")
@RequestMapping("/drive/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("list")
    @ApiOperation("用戶数据表-获取用户数据")
    public Result<List<UserResultDto>> getUserList(@RequestBody UserGetDto dto) {
        return null;
    }

    @PostMapping("addOrUpdate")
    @ApiOperation("用戶数据表-新增或更新用户数据")
    public Result<Boolean> addOrUpdate(@Valid @RequestBody UserGetDto dto) {
        boolean flag = userService.addOrUpdate(dto);
        return Result.JudgeBooleanValue(flag);
    }

    @PostMapping("list")
    @ApiOperation("用戶数据表-获取用户数据")
    public Result<Boolean> deleteById(@RequestBody UserGetDto dto) {
        boolean flag = userService.deleteById(dto);
        return Result.JudgeBooleanValue(flag);
    }
}
