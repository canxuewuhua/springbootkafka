package com.king.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author yongqiang.zhu
 * @date 2019/3/31 22:44
 */
@Component
@Slf4j
public class KafkaConsumerServer {
	/**
	 * id：消费者的id，当GroupId没有被配置的时候，默认id为GroupId
	 * topics：需要监听的Topic，可监听多个
	 */
	@KafkaListener(id = "tianji", topics = "kafka20190331test")
	public void listen(ConsumerRecord<?, ?> record) {
		System.out.println(record.key());
		System.out.println(record.value());
	}
}
