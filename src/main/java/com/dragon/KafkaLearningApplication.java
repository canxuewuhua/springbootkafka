package com.dragon;

import com.dragon.provider.KafkaSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 版本选择：
 * kafka服务器的版本
 * wget https://archive.apache.org/dist/kafka/0.11.0.2/kafka_2.11-0.11.0.2.tgz
 * spring-kafka依赖的版本
 * <dependency>
 * <groupId>org.springframework.kafka</groupId>
 * <artifactId>spring-kafka</artifactId>
 * <version>2.1.3.RELEASE</version>
 * </dependency>
 */
@SpringBootApplication
public class KafkaLearningApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(KafkaLearningApplication.class, args);

		KafkaSender sender = context.getBean(KafkaSender.class);

		for (int i = 0; i < 3; i++) {
			//调用消息发送类中的消息发送方法
			sender.send();

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
