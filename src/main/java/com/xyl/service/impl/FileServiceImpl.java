package com.xyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyl.entity.dao.FileMapper;
import com.xyl.entity.pojo.File;
import com.xyl.service.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Override
    public List<File> getAllFileInfo(String memId) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getMemId, memId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public  List<File> getFileInfo(String id) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getId, id);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 获取当前目录下的所有文件
     */
    @Override
    public List<File> getCurFiles(String dir,String id) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getFDir, dir);
        wrapper.eq(File::getMemId, id);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public File getFiles(String id) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getId, id);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<File> getFindFile(String memid,String name) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getMemId, memid);
        wrapper.like(File::getName, name);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<File> getList(String userId, String url,int result,String name) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(File::getMemId,userId);
        wrapper.like(File::getUrl,url);
        List<File> fileList = baseMapper.selectList(wrapper);
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
}
