package com.king;

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

/**
 * kafka启动步骤
 * 1、在zookeeper目录下启动zookeeper
 *    ./zkServer.sh start
 * 2、后台启动kafka命令
 *    ./kafka-server-start.sh ../config/server.properties 1>/dev/null 2>&1 &
 * 启动后，查看进程 ，使用jps命令会显示：Jps QuorumPeerMain，启动之后会出现kafka ，启动服务者和消费者后，出现ConsoleProducer，ConsoleConsumer
 * 3、创建主题
 * ./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
 * 4、查看主题列表
 * ./kafka-topics.sh --list --zookeeper localhost:2181
 * 5、服务者发送消息
 * ./kafka-console-producer.sh --broker-list 192.168.83.150:9092 --topic test
 *   this is message
 * 6、消费者接收消息
 * ./kafka-console-consumer.sh --bootstrap-server 192.168.83.150:9092 --topic test --from-beginning
 *   this is message
 */
@SpringBootApplication
public class KafkaLearningApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context =SpringApplication.run(KafkaLearningApplication.class, args);
	}
}
