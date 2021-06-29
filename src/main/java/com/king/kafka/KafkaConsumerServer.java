package com.king.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yongqiang.zhu
 */
@Component
@Slf4j
public class KafkaConsumerServer {
	/**
	 * id：消费者的id，当GroupId没有被配置的时候，默认id为GroupId
	 * topics：需要监听的Topic，可监听多个
	 */
	@KafkaListener(id = "nanjing", topics = "kafka20210612test")
	public void listen(ConsumerRecord<?, ?> record) {
		log.info("消费者接收的record.key:", record.key());
		log.info("消费者接收的record.value:", record.value());
		Optional<?> kafkaMessage = Optional.ofNullable(record.value());
		if (kafkaMessage.isPresent()) {
			Object message = kafkaMessage.get();
			log.info("----------------- 消费者接收的record:{}" , record);
			log.info("------------------消费者接收的message:{}" ,message);
		}
	}
}
