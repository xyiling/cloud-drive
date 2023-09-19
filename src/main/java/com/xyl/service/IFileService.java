package com.xyl.service;

import com.xyl.entity.pojo.File;

import java.util.List;

public interface IFileService {
    List<File> getAllFileInfo(String memId);

    List<File> getFileInfo(String id);

    List<File> getCurFiles(String userDir,String id);

    File getFiles(String id);

    List<File> getFindFile(String userId, String name);

    List<File> getList(String userId, String url,int result,String name);
}
