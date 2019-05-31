# springbootkafka

# 确保机器上安装了jdk，kafka需要java运行环境，以前的kafka还需要zookeeper，
# 新版的kafka已经内置了一个zookeeper环境，所以我们可以直接使用。




# 在本地linux虚拟机上安装好kafka 版本为2.11

# zookeeper单机和kafka单机实现消息通信
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

# zookeeper集群和kafka集群实现kafka消息通信
# kafka集群配置步骤
# 1、 linux虚拟机三台 192.168.199.239  192.168.199.225 192.168.199.143 
#     将上述三台机器centos7本身自带的openjdk卸载掉，安装 jdk-8u181-linux-x64.tar.gz
#     java -version  查找他们的安装位置  rpm -qa | grep java （删除全部，noarch文件可以不用删除）
#     vim  /etc/profile  命令打开 profile 文件盘配置环境变量
#     export JAVA_HOME=/usr/local/java/jdk1.8.0_11
#     export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
#     export PATH=$PATH:$JAVA_HOME/bin
#     source /etc/profile  命令使刚才配置的环境变量生效
# 2、 在三台机器上安装zookeeper zookeeper-3.4.9.tar.gz
#    ① 在/usr/local/app/zookeeper-3.4.9/conf下 复制zoo_sample.cfg为zoo.cfg，并修改zoo.cfg
#      注释掉dataDir，修改为下方
#      dataDir=/usr/local/app/zookeeper-3.4.9/data
#      dataLogDir=/usr/local/app/zookeeper-3.4.9/logs
#      文件末尾加上
#      server.1=192.168.199.239:2888:3888
#      server.2=192.168.199.225:2888:3888
#      server.3=192.168.199.143:2888:3888
#     端口都为clientPort=2181即可
#     ② 在/usr/local/app/zookeeper-3.4.9下创建data文件夹，在data目录下创建文件myid 命令为touch myid，然后使用命令 echo "1">myid
#        其他两台linux虚拟机使用同样的方式进行创建，改为2 3
# 3、在/usr/local/app/zookeeper-3.4.9/bin下使用   ./zkServer.sh start 启动zookeeper，使用./zkServer.sh status查看创建，三台虚拟机
#     会有一台显示leader，两台显示follower
#     另外注意防火墙要关闭  命令如下
#     firewalld的基本使用
#        启动： systemctl start firewalld
#       关闭： systemctl stop firewalld
#       查看状态： systemctl status firewalld 
#       开机禁用  ： systemctl disable firewalld
#      开机启用  ： systemctl enable firewalld
# 4、安装kafka，在三台虚拟机上安装 kafka_2.11-0.11.0.1.tgz
#    进入/usr/local/app/kafka/config文件夹下，编辑server.properties
#     broker.id=2
#     listeners=PLAINTEXT://192.168.199.225:9092
#     advertised.listeners=PLAINTEXT://192.168.199.143:9092
#     host.name=192.168.199.225
#     log.retention.hours=168
#     message.max.byte=5242880
#     default.replication.factor=2
#     replica.fetch.max.bytes=5242880
     
#     zookeeper.connect=192.168.199.239:2181,192.168.199.225:2181,192.168.199.143:2181
     
#     启动kafka 在/usr/local/app/kafka/bin下使用命令  ./kafka-server-start.sh -daemon ../config/server.properties
#     可以使用jps命令查看kafka的进程号
# 5、安装kafka-manager
#      参考网址https://www.cnblogs.com/dadonggg/p/8205302.html
#      下载kafka-manager-1.3.3.7.zip
#      在/usr/local/app/kafka-manager/kafka-manager-1.3.3.7/conf文件下 编辑配置文件application.conf
      
#     注释掉kafka-manager.zkhosts="localhost:2181"       ##注释这一行，下面添加一行
#     kafka-manager.zkhosts="10.0.0.50:12181,10.0.0.60:12181,10.0.0.70:12181"
#     在/usr/local/app/kafka-manager/kafka-manager-1.3.3.7/bin下  执行 ./kafka-manager 启动kafka-manager
#     在浏览器输入 http://192.168.199.239:9000/即可启动kafka管理器，可以看到集群，主题，消费者，集群的创建选择最接近的kafka版本
# 6、在springboot项目下创建生产者，使用kefkaTemplate进行数据发送，消费者进行监听，使用@KafkaListener注解
                
