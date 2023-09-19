package com.xyl.service;

import com.xyl.entity.dto.request.RegisterVo;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.pojo.User;

public interface IUserService {
    boolean deleteById(UserGetDto dto);

    boolean addOrUpdate(UserGetDto dto);
    //登录的方法
    String login(UserGetDto dto);

    User login1(UserGetDto dto);
    //注册的方法
    void register(RegisterVo registerVo);
    User getUserById(String userId);
}
