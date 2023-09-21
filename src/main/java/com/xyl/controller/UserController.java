package com.xyl.controller;

import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.CommonIdDto;
import com.xyl.entity.dto.request.RegisterVo;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.dto.response.UserResultDto;
import com.xyl.service.UserService;
import com.xyl.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xyl
 * 用户管理：
 * <ul>
 *     <li>用户登录</li>
 *     <li>用户注册</li>
 *     <li>用户退出</li>
 *     <li>用户注销</li>
 *     <li>密码重置</li>
 *     <li>用户查询</li>
 *     <li>用户信息新增、更新与删除</li>
 * </ul>
 */
@RestController
@Api("用户表-用户管理")
@RequestMapping("/cloud-drive/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("list")
    @ApiOperation("用戶数据表-获取用户数据")
    public Result<List<UserResultDto>> getList(@Validated @RequestBody UserGetDto dto) {
//        userService.getUserList()
        return null;
    }

    @PostMapping("addOrUpdate")
    @ApiOperation("用戶数据表-新增或更新用户数据")
    public Result<Boolean> addOrUpdate(@Validated @RequestBody UserGetDto dto) {
        return ResultUtil.judgeBooleanValue(userService.addOrUpdate(dto));
    }

    /**
     * 用户登录
     * 计划使用 spring security
     */
    @ApiOperation(value = "用户-登录")
    @PostMapping("login")
    public Result login(@Validated @RequestBody UserGetDto dto) {
        return null;
    }

    /**
     * 用户退出操作
     * spring security
     */
    @ApiOperation("用户-下线")
    @PostMapping("logout")
    public Result<Boolean> logout(@Validated @RequestBody UserGetDto dto) {
        return null;
    }

    /**
     * 注册
     */
    @ApiOperation("用户-注册")
    @PostMapping("register")
    public Result<Boolean> register(@Validated @RequestBody RegisterVo registerVo) {
        return userService.register(registerVo);
    }

    /**
     * 查询用户信息
     */
    @ApiOperation(value = "用户-根据id查询用户")
    @PostMapping("getById")
    public Result getById(@Validated @RequestBody CommonIdDto dto) {
        return userService.getUserById(dto);
    }

    @ApiOperation("用户-注销")
    @PostMapping("deleteById")
    public Result<Boolean> deleteById(@Validated @RequestBody UserGetDto dto) {
        return userService.deleteById(dto);
    }
}
