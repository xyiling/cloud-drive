package com.xyl.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileGetDto {
    private List<Integer> idList;
    private String filename;
    private MultipartFile file;
    private String filepath;
    private Boolean star;

}
