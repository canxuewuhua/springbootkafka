# springbootkafka

# 确保机器上安装了jdk，kafka需要java运行环境，以前的kafka还需要zookeeper，
# 新版的kafka已经内置了一个zookeeper环境，所以我们可以直接使用。




# 在本地linux虚拟机上安装好kafka 版本为2.11

# 启动zookeeper
# 进入kafka的安装bin目录下执行命令  ./zookeeper-server-start.sh /usr/local/app/kafka/config/zookeeper.properties
# 当提示：INFO binding to port 0.0.0.0/0.0.0.0:2181 (org.apache.zookeeper.server.NIOServerCnxnFactory)说明zk启动成功

# 启动kafka
# 进入kafka的安装bin目录下执行命令 ./kafka-server-start.sh /usr/local/app/kafka/config/server.properties
# 当提示：INFO [Kafka Server 0], started (kafka.server.KafkaServer) 说明kafka启动成功

# 创建第一个消息
# 创建一个Topic，在bin目录下执行命令
# ./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic 20190331test
# 提示：Created topic "20190331test". 说明创建topic成功

# 在创建topic后可以在bin目录下通过输入
# ./kafka-topics.sh --list --zookeeper localhost:2181
# 20190331test  
# 来查看已经创建的topic

# 创建一个消息消费者
# 在bin目录下执行命令 注：ip地址写成虚拟机的地址，localhost不行，访问不通，会提示，所以命令换成真实ip
# WARN Connection to node -1 could not be established. Broker may not be available. (org.apache.kafka.clients.NetworkClient)

# ./kafka-console-consumer.sh --bootstrap-server 192.168.199.239:9092 --topic 20190331test --from-beginning
# 此时消费者端不会打印出任何数据

#创建一个消费生产者
# 在bin目录下执行命令
# ./kafka-console-producer.sh --broker-list 192.168.199.239:9092 --topic 20190331test
# 控制台打印  ">" ,此时可以输入message
# 消费端会显示出生产者发来的message

# --------上面演示的是在linux机器上生产者和消费者发送和接收消息的过程，也可以在java程序中实现kafka通信--------