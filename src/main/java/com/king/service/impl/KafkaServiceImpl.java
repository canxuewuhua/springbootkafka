package com.king.service.impl;

import com.king.service.KafkaService;
import com.king.service.PushMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ZHUYONGQIANG on 2018/7/3.
 */
@Service
public class KafkaServiceImpl implements KafkaService{

    private Logger logger = LoggerFactory.getLogger(KafkaServiceImpl.class);
    private static final String  SEND_FLAG = "1";

    @Autowired
    private PushMessageService pushMessageService;
    @Override
    public void sendKafkaLog() {
        if(SEND_FLAG.equals("1")){
            pushMessageToKafka("NBA西部总决赛：Hello：火箭-勇士");
        }
    }

    private void pushMessageToKafka(String mess) {
        try {
            pushMessageService.pushRepaymentInfo(mess);
        } catch (Exception e) {
            logger.error("fail to push kafka repayment info !", e);
        }
    }


    /**
     * 推送还款信息到kafka日志
     *
     */
    public void insertFinKafkaLogForRepayment(String mess) {
        logger.info("消息：{}",mess);
    }


}
