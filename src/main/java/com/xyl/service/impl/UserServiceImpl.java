package com.xyl.service.impl;

import com.xyl.entity.dao.UserMapper;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.pojo.User;
import com.xyl.service.IUserService;
import com.xyl.util.converter.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    @Override
    public boolean deleteById(UserGetDto dto) {
        return userMapper.deleteById(dto.getId()) > 0;
    }

    @Override
    public boolean addOrUpdate(UserGetDto dto) {
        User user = userConverter.convertGetDto2pojo(dto);
        boolean flag;
        if (dto.getId() == null) {
            flag = userMapper.insert(user) > 0;
        } else {
            flag = userMapper.updateById(user) > 0;
        }
        return flag;
    }
}
