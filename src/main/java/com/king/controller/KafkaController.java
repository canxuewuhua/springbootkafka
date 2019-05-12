package com.king.controller;

import com.king.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
