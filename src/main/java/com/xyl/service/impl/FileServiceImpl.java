package com.xyl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyl.entity.pojo.File;
import com.xyl.service.IFileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements IFileService {
    @Override
    public List<File> getAllFileInfo(String memId) {
        QueryWrapper<File> wrapper=new QueryWrapper<>();
        wrapper.eq("mem_id",memId);
        //File files = baseMapper.selectById(wrapper);
        List<File> fileList = baseMapper.selectList(wrapper);
        //System.out.println(files);
        return fileList;
    }

    @Override
    public  List<File> getFileInfo(String id) {
        QueryWrapper<File> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        //File files = baseMapper.selectById(wrapper);
        List<File> files = baseMapper.selectList(wrapper);
        //System.out.println(files);
        return files;
    }

    /**
     * 获取当前目录下的所有文件
     * @param dir
     * @return
     */
    @Override
    public List<File> getCurFiles(String dir,String id) {
        QueryWrapper<File> wrapper=new QueryWrapper<>();
        wrapper.eq("f_dir",dir);
        wrapper.eq("mem_id",id);
        //File files = baseMapper.selectById(wrapper);
        List<File> files = baseMapper.selectList(wrapper);
        return files;
    }

    @Override
    public File getFiles(String id) {
        QueryWrapper<File> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        File file = baseMapper.selectOne(wrapper);
        return file;
    }

    @Override
    public List<File> getFindFile(String memid,String name) {
        QueryWrapper<File> wrapper=new QueryWrapper<>();
        wrapper.eq("mem_id",memid);
        wrapper.like("name",name);
        List<File> fileList = baseMapper.selectList(wrapper);
        return fileList;
    }

    @Override
    public List<File> getList(String userId, String url,int result,String name) {
        LambdaQueryWrapper<File> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(File::getMemId,userId);
        wrapper.like("f_dir",url);
        List<File> fileList = baseMapper.selectList(wrapper);
        //File file = baseMapper.selectById(wrapper);
        //System.out.println(file);
        for (int i = 0; i < fileList.size(); i++) {
            String fDir = fileList.get(i).getFDir();
            String[] s=fDir.split("/",-1);
            s[result]=name;
            //String list=Arrays.toString(s);
            //System.out.println(Arrays.toString(s));
            StringBuffer sb = new StringBuffer();
            //sb.append("/");
            for (int j = 1; j < s.length; j++) {
                sb.append("/").append(s[j]);
            }
            System.out.println(sb.toString());
            fileList.get(i).setFDir(sb.toString());
            //System.out.println(fileList);
        }

        System.out.println(fileList);
        //System.out.println(fileList);
        return fileList;
    }
}
