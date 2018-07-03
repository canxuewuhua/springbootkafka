package com.king.kafka;

import com.alibaba.fastjson.JSON;
import com.king.service.impl.KafkaServiceImpl;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.ProducerListener;

import javax.annotation.Resource;

public class KafkaProducerListener implements ProducerListener {
    private final Logger LOG = LoggerFactory.getLogger(KafkaProducerListener.class);


    @Resource(name = "kafkaServiceImpl")
    private KafkaServiceImpl kafkaServiceImpl;

    /**
     * 发送消息成功后调用
     */
    @Override
    public void onSuccess(String topic, Integer partition, Object key,
                          Object value, RecordMetadata recordMetadata) {
        LOG.debug("==========kafka发送数据成功（日志开始）==========");
        LOG.debug("----------topic:" + topic);
        LOG.debug("----------partition:" + partition);
        LOG.debug("----------key:" + key);
        LOG.debug("----------value:" + value);
        LOG.debug("----------RecordMetadata:" + recordMetadata);
        LOG.debug("~~~~~~~~~~kafka发送数据成功（日志结束）~~~~~~~~~~");

        kafkaServiceImpl.insertFinKafkaLogForRepayment("推送消息-success...");
    }

    /**
     * 发送消息错误后调用
     */
    @Override
    public void onError(String topic, Integer partition, Object key,
                        Object value, Exception exception) {
        LOG.debug("==========kafka发送数据错误（日志开始）==========");
        LOG.debug("----------topic:" + topic);
        LOG.debug("----------partition:" + partition);
        LOG.debug("----------key:" + key);
        LOG.debug("----------value:" + value);
        LOG.debug("----------Exception:" + exception);
        LOG.debug("~~~~~~~~~~kafka发送数据错误（日志结束）~~~~~~~~~~");
        exception.printStackTrace();


        kafkaServiceImpl.insertFinKafkaLogForRepayment("推送消息-error...");
    }

    /**
     * 方法返回值代表是否启动kafkaProducer监听器
     */
    @Override
    public boolean isInterestedInSuccess() {
        LOG.debug("///kafkaProducer监听器启动///");
        return true;
    }
}
