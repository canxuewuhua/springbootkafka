package com.king.service;

import com.king.kafka.KafkaProducerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZHUYONGQIANG on 2018/7/3.
 */
@Service("pushMessageService")
public class PushMessageService {
    private Logger logger = LoggerFactory.getLogger(PushMessageService.class);

    @Value("${kafka.test_topic}")
    private String topic;
    private static String role = "tianji";

    @Autowired
    private KafkaProducerServer kafkaProducerServer;

    private ExecutorService exec;

    public PushMessageService() {
        exec = Executors.newFixedThreadPool(5);
    }

    public void pushRepaymentInfo(String info) {
        exec.execute(new Thread(new Runnable() {
            @Override
            public void run() {
                exec(info);
            }
        }));
    }

    public void pushRepaymentInfo(List<String> list) {
        exec.execute(new Runnable() {
            @Override
            public void run() {
                for (String  mess : list) {
                    exec(mess);
                }
            }
        });
    }

    private void exec(String mess) {
        HashMap<String, String> param = new HashMap<>();
        param.put("mess", mess);
        logger.info("push kafka message to " + role + " --> " + mess);
        kafkaProducerServer.sendMesForTemplate(topic, mess, "1",
                1, role);
    }
}
