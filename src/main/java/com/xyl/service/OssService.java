package com.xyl.service;

import com.xyl.entity.pojo.File;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    File upload(MultipartFile file, String catalogue);

    String delete(String id);

    String uploadFile(MultipartFile file);

    String deleteVideo(String id);

    String uploadFileAvatar(MultipartFile file);
}
