package com.xyl.service;

import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.CommonIdDto;
import com.xyl.entity.dto.request.RegisterVo;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.pojo.User;

public interface UserService {
    Result<Boolean> deleteById(UserGetDto dto);

    boolean addOrUpdate(UserGetDto dto);
    //注册的方法
    Result<Boolean> register(RegisterVo registerVo);
    Result getUserById(CommonIdDto userId);
}
