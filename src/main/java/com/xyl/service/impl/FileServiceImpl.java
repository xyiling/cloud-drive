package com.xyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.entity.dao.FileMapper;
import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.CommonIdDto;
import com.xyl.entity.dto.request.FileGetDto;
import com.xyl.entity.pojo.File;
import com.xyl.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public List<File> getList(String userId, String url,int result,String name) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getMemId,userId);
        wrapper.like(File::getUrl,url);
        List<File> fileList = fileMapper.selectList(wrapper);
        for (File file : fileList) {
            String fDir = file.getFDir();
            String[] s = fDir.split("/", -1);
            s[result] = name;
            StringBuffer sb = new StringBuffer();
            for (int j = 1; j < s.length; j++) {
                sb.append("/").append(s[j]);
            }
            file.setFDir(sb.toString());
        }
        return fileList;
    }

    @Override
    public Result<Boolean> addOrUpdate(FileGetDto dto) {
        // todo 在这里调用AliyunOss提供的服务，而不是再创建一个OssController，多余
        return null;
    }

    @Override
    public Result<List<File>> getFileList(FileGetDto dto) {
        return null;
    }

    @Override
    public Boolean deleteById(CommonIdDto dto) {
        return null;
    }
}
