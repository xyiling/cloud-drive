package com.xyl.service;

import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.RegisterVo;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.pojo.User;

public interface UserService {
    boolean deleteById(UserGetDto dto);

    boolean addOrUpdate(UserGetDto dto);
    //登录的方法
    String login(UserGetDto dto);

    User login1(UserGetDto dto);
    //注册的方法
    Result<Boolean> register(RegisterVo registerVo);
    Result getUserById(UserGetDto userId);
}
