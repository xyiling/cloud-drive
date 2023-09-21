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


    //登录的方法
    public String login(User user) {
        //获取登录手机号和密码
        String mobile = user.getMobile();
        String password = user.getPassword();
        System.out.println(mobile + password);
        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new SpaceException(201, "登录失败");
        }
        //判断手机号是否正确
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile, mobile);
        User mobileuser = userMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if (mobileuser == null) {//没有这个手机号
            throw new SpaceException(20001, "登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if (!MD5.encrypt(password).equals(mobileuser.getPassword())) {
            throw new SpaceException(20001, "登录失败");
        }
        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtil.getJwtToken(mobileuser.getId(), mobileuser.getNickname());
        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(User::getMobile, user.getMobile());
        User User = userMapper.selectOne(wrapper1);
        return jwtToken;
    }

    //登录的方法
    public User login1(User user) {
        //获取登录手机号和密码
        String mobile = user.getMobile();
        String password = user.getPassword();
        System.out.println(mobile + password);
        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new SpaceException(20001, "手机号或密码为空，登录失败");
        }
        //判断手机号是否正确
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile, mobile);
        User mobileuser = userMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if (mobileuser == null) {//没有这个手机号
            throw new SpaceException(20001, "未注册，登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 MD5
        if (!MD5.encrypt(password).equals(mobileuser.getPassword())) {
            throw new SpaceException(20001, "登录失败");
        }
        //登录成功
        //生成token字符串，使用jwt工具类
        //String jwtToken = JwtUtil.getJwtToken(mobileuser.getId(), mobileuser.getNickname());
        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(User::getMobile, user.getMobile());
        return userMapper.selectOne(wrapper1);
    }

    //注册的方法
    public Result register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码
        String avatar = registerVo.getAvatar();//头像
        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)
        ) {
            throw new SpaceException(20001, "不能为空");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        System.out.println(redisCode);
        if (!code.equals(redisCode)) {
            throw new SpaceException(20001, "验证码错误");
        }


        //判断手机号是否重复，表里面存在相同手机号不进行添加
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new SpaceException(20001, "手机号已存在，注册失败！");
        }
        //数据添加数据库中
        User user = new User();
        user.setMobile(mobile);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setPassword(MD5.encrypt(password));//密码需要加密的
        baseMapper.insert(user);
        return null;
    }

}
