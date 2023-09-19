package com.xyl.entity.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户目录对象")
public class UserDir implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "mem_id",type = IdType.AUTO)
    private String memId;

    @TableField("mem_dir")
    private String memDir;
}