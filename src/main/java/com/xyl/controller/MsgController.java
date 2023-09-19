package com.xyl.controller;

import com.xyl.entity.dto.Result;
import com.xyl.service.IMsgService;
import com.xyl.util.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Api("信息数据-消息管理")
@RequestMapping("/drive/msg/")
@CrossOrigin
public class MsgController {

    private final IMsgService msgService;
    private final RedisTemplate<String, String> redisTemplate;

    public MsgController(IMsgService msgService, RedisTemplate<String, String> redisTemplate) {
        this.msgService = msgService;
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation(value = "发送验证码")
    @GetMapping("send/{phone}")
    public Result sendMsg(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.fail("验证码生成失败");
        }
        //生成随机验证码
        code = RandomUtil.getFourBitRandom();
        boolean isSend = msgService.send(code, phone);
        if (isSend) {
            //发送成功放到redis里面
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return Result.ok("短信发送成功");
        } else {
            return Result.fail("短信发送失败");
        }
    }
}