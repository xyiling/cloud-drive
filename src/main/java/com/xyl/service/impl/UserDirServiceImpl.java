package com.xyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyl.entity.dao.UserDirMapper;
import com.xyl.entity.pojo.File;
import com.xyl.entity.pojo.UserDir;
import com.xyl.service.IFileService;
import com.xyl.service.IUserDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDirServiceImpl implements IUserDirService {

    @Autowired
    private IFileService fileService;
    @Autowired
    private UserDirMapper userDirMapper;

    @Override
    public UserDir getUserDir(String id) {
        return userDirMapper.selectById(id);
    }

    @Override
    public int setUserDir(UserDir userDir) {
        return userDirMapper.updateById(userDir);
    }

    @Override
    public boolean deleteStruct(String memid, String url) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<File>();
        wrapper.like(File::getFDir, url);
        wrapper.eq(File::getMemId, memid);
        boolean b = fileService.remove(wrapper);
        return b;
    }
}
