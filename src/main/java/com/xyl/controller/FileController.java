package com.xyl.controller;

import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.CommonIdDto;
import com.xyl.entity.dto.request.FileGetDto;
import com.xyl.entity.pojo.File;
import com.xyl.service.FileService;
import com.xyl.service.UserDirService;
import com.xyl.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xyl
 * @since 2021-06-06
 */
@RestController
@RequestMapping("/cloud-drive/file")
@CrossOrigin
@Api("文件操作")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserDirService userDirService;

    /**
     * 查询当前用户下的所有文件
     * 当前用户怎么获取：用户登录信息存放在session，直接在session拿用户id即可，不必传入
     */
    @ApiOperation(value = "文件-条件查询当前路径所有文件")
    @PostMapping("getList")
    public Result<List<File>> getList(@Validated @RequestBody FileGetDto dto) {
        return fileService.getFileList(dto);
    }

    /**
     * 上传或更新文件
     */
    @ApiOperation(value = "文件-上传或更新文件")
    @PostMapping("addOrUpdate")
    public Result<Boolean> addOrUpdate(@Validated @RequestBody FileGetDto dto) {
        // todo 文件收藏功能、文件重命名功能等文件信息更新功能、文件上传功能
        return fileService.addOrUpdate(dto);
    }

    // todo 文件删除
    @ApiOperation("文件-删除文件")
    @PostMapping("deleteById")
    public Result<Boolean> deleteById(@Validated @RequestBody CommonIdDto dto) {
        return ResultUtil.judgeBooleanValue(fileService.deleteById(dto));
    }
}
