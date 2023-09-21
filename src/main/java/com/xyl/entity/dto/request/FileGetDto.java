package com.xyl.entity.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FileGetDto {
    private List<Integer> idList;
    private String filename;
}
