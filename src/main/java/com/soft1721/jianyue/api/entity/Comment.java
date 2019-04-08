package com.soft1721.jianyue.api.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Comment {
    private Integer id;
    private Integer uId;
    private Integer aId;
    private String content;
    private Date commentTime;
}
