package com.king.kafka;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service("kafkaProducerServer")
public class KafkaProducerServer {
    private Logger logger = LoggerFactory.getLogger(KafkaProducerServer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * kafka发送消息模板
     *
     * @param topic        主题
     * @param value        messageValue
     * @param ifPartition  是否使用分区 0是\1不是
     * @param partitionNum 分区数 如果是否使用分区为0,分区数必须大于0
     * @param role         角色:bbc app erp...
     */
    public Map<String, Object> sendMesForTemplate(String topic, Object value, String ifPartition,
                                                 Integer partitionNum, String role) {
        String key = role + "-" + value.hashCode();
        String valueString = JSON.toJSONString(value);
        if (ifPartition.equals("0")) {
            //表示使用分区
            int partitionIndex = getPartitionIndex(key, partitionNum);
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, partitionIndex, key, valueString);
            Map<String, Object> res = checkProRecord(result);
            return res;
        } else {
            ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, key, valueString);
            result.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                public void onFailure(Throwable throwable) {
                    System.out.println("失败");
                }
                public void onSuccess(SendResult<String, String> integerStringSendResult) {
                    logger.info("==========kafka发送数据成功（日志开始）==========");
                    logger.info("----------topic:"+topic);
                    logger.info("----------partition:"+partitionNum);
                    logger.info("----------key:"+key);
                    logger.info("----------value:"+value);
                    logger.info("----------RecordMetadata:"+integerStringSendResult.getRecordMetadata().partition());
                    logger.info("~~~~~~~~~~kafka发送数据成功（日志结束）~~~~~~~~~~");
                    System.out.println("成功");
                }
            });
            Map<String, Object> res = checkProRecord(result);
            return res;
        }
    }

    /**
     * 根据key值获取分区索引
     *
     * @param key
     * @param partitionNum
     * @return
     */
    private int getPartitionIndex(String key, int partitionNum) {
        if (key == null) {
            Random random = new Random();
            return random.nextInt(partitionNum);
        } else {
            int result = Math.abs(key.hashCode()) % partitionNum;
            return result;
        }
    }

    /**
     * 检查发送返回结果record
     *
     * @param res
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> checkProRecord(ListenableFuture<SendResult<String, String>> res) {
        Map<String, Object> m = new HashMap<String, Object>();
        if (res != null) {
            try {
                SendResult r = res.get();//检查result结果集
                /*检查recordMetadata的offset数据，不检查producerRecord*/
                Long offsetIndex = r.getRecordMetadata().offset();
                if (offsetIndex != null && offsetIndex >= 0) {
                    m.put("code", KafkaMesConstant.SUCCESS_CODE);
                    m.put("message", KafkaMesConstant.SUCCESS_MES);
                    return m;
                } else {
                    m.put("code", KafkaMesConstant.KAFKA_NO_OFFSET_CODE);
                    m.put("message", KafkaMesConstant.KAFKA_NO_OFFSET_MES);
                    return m;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
                return m;
            } catch (ExecutionException e) {
                e.printStackTrace();
                m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
                m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
                return m;
            }
        } else {
            m.put("code", KafkaMesConstant.KAFKA_NO_RESULT_CODE);
            m.put("message", KafkaMesConstant.KAFKA_NO_RESULT_MES);
            return m;
        }
    }

}
