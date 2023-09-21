package com.xyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyl.entity.dao.UserMapper;
import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.RegisterVo;
import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.pojo.User;
import com.xyl.service.UserService;
import com.xyl.util.JwtUtil;
import com.xyl.util.MD5;
import com.xyl.util.ResultUtil;
import com.xyl.util.converter.UserConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result<Boolean> deleteById(UserGetDto dto) {
        return ResultUtil.judgeBooleanValue(userMapper.deleteById(dto.getId()) > 0);
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

    @Override
    public String login(UserGetDto dto) {
        return null;
    }

    @Override
    public User login1(UserGetDto dto) {
        return null;
    }


    @Override
    public Result getUserById(UserGetDto userId) {
        return null;
    }

}
