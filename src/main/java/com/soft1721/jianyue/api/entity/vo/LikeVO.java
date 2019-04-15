package com.soft1721.jianyue.api.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class LikeVO {
    private Integer aId;
    private String title;
    private Date createTime;
    private String nickname;
}
