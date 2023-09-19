package com.xyl.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class User {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("用户表主键")
    private Integer id;

    @TableField("uid")
    @ApiModelProperty("用户id")
    private String uid;

    @TableField("nickname")
    @ApiModelProperty("用户昵称")
    @NotNull
    private String nickname;

    @TableField("avatar")
    @ApiModelProperty("用户头像")
    private String avatar;

    @TableField(exist = false)
    @ApiModelProperty("乐观锁")
    private Integer version;
}
