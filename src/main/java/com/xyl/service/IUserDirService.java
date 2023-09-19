package com.xyl.service;

import com.xyl.entity.pojo.UserDir;

public interface IUserDirService {
    UserDir getUserDir(String id);

    int setUserDir(UserDir userDir);

    boolean deleteStruct(String userId, String url);
}
