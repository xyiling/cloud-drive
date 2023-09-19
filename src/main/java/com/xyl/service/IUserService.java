package com.xyl.service;

import com.xyl.entity.dto.request.UserGetDto;

public interface IUserService {
    boolean deleteById(UserGetDto dto);

    boolean addOrUpdate(UserGetDto dto);
}
