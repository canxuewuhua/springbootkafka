package com.king.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by ZHUYONGQIANG on 2018/6/12.
 */
@Data
public class Message {
    private Long id;    //id
    private String msg; //消息
    private Date sendTime;  //时间戳
}
