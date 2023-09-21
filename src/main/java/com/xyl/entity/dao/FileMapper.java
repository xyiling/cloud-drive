package com.xyl.entity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyl.entity.pojo.File;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface FileMapper extends BaseMapper<File> {
}
