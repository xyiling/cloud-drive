package com.xyl.service;

import com.xyl.entity.dto.Result;
import com.xyl.entity.dto.request.CommonIdDto;
import com.xyl.entity.dto.request.FileGetDto;
import com.xyl.entity.pojo.File;

import java.util.List;

public interface FileService {
    List<File> getList(String userId, String url,int result,String name);

    Result<Boolean> addOrUpdate(FileGetDto dto);

    Result<List<File>> getFileList(FileGetDto dto);

    Boolean deleteById(CommonIdDto dto);
}
