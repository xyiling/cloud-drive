package com.xyl.controller;

import com.xyl.entity.dto.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xyl
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/drive/member")
@CrossOrigin
//@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;


}
