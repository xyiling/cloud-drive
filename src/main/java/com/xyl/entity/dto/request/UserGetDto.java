package com.xyl.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGetDto {
    private Integer id;
    private String uid;
    private List<String> nicknameList;
}
