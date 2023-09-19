package com.xyl.util.converter;

import com.xyl.entity.dto.request.UserGetDto;
import com.xyl.entity.dto.response.UserResultDto;
import com.xyl.entity.pojo.User;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface UserConverter {
    @Mappings({
            @Mapping(source = "dto.uid", target = "uid"),
    })
    User convertGetDto2pojo(UserGetDto dto);

    @Mappings({
            @Mapping(source = "user.uid", target = "uid"),
    })
    UserResultDto convertPojo2ResultDto(User user);
}
