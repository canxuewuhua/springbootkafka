package com.king.controller;

import com.king.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZHUYONGQIANG on 2018/7/3.
 */
@RestController
@RequestMapping(value = {"/v1/kafka"})
public class KafkaController {

    @Autowired
    private KafkaService kafkaService;
    @RequestMapping(value = "/test")
    @ResponseBody
    public void sendKafkaLog() {
        kafkaService.sendKafkaLog();
    }
}
