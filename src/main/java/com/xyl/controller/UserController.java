package com.xyl.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.RegisterVo;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.dto.response.UserResultDto;
import com.xyl.entity.pojo.User;
import com.xyl.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //登录
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public Result loginUser(@RequestBody UserGetDto dto) {
        //user对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = userService.login(dto);
        User user = userService.login1(dto);
        //System.out.println(mem);
        return Result.ok().data("token", token).data("mem", mem);
    }

    //注册
    @PostMapping("register")
    public Result registerUser(@RequestBody RegisterVo registerVo) {
        boolean flag = userService.register(registerVo) > 0;
        return Result.ok();
    }

    //查询用户信息
    @ApiOperation(value = "根据用户表id查询用户信息")
    @GetMapping("getuserInfo/{id}")
    public Result getuserInfo(@PathVariable String id) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq("id", id);
        User User = userService.getOne(wrapper);
        return Result.ok().data("user", User);
    }

    //修改用户信息
    @ApiOperation(value = "更新用户信息")
    @PostMapping("updateuserInfo")
    public Result updateuserInfo(@RequestBody UserGetDto dto) {
        String id = dto.getId();
        LambdaQueryWrapper<User> w = new LambdaQueryWrapper<>();
        w.eq("id", id);
        User one = userService.getOne(w);
        User user = new User();
        user.setId(User.getId());
        user.setNeicun(one.getNeicun());
        user.setAvatar(User.getAvatar());
        user.setNickname(User.getNickname());
        boolean b = userService.updateById(user);
        if (b) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}
