## 说明
- 项目启动前置条件：
    - 启动本地zookeeper
        - 如果配置了环境变量，并且path中有zookeeper的bin文件夹路径，则直接使用zkServer命令就可以启动
    - 启动本地kafka
        - 在kafka安装的根目录执行命令：`.\bin\windows\kafka-server-start.bat .\config\server.properties`
    - 创建一个topic
        - 在\bin\windows路径下执行命令：`kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test`
    - 启动本地生产者
        - 在\bin\windows路径下执行命令：`kafka-console-producer.bat --broker-list localhost:9092 --topic test`
    - 启动本地生产者
        - 在\bin\windows路径下执行命令：`kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning`

- kafka正常启动并能收发数据，则可尝试启动本项目，注意项目的配置文件以及发送消息接口

## Demo中的两种kafka client实现
- spring-boot整合kafka的生产者消费者示例
    - 该Kafka实现方式下集成了三种简单的序列化行为
        - String采用Kryo序列化，最通用的序列化方式，许多无法序列化的对象可以转换为JSON后通过字符串后序列化
        - POJO采用Java Serialize，不涉及到POJO的具体结构，压缩效率不高，不建议采用
        - POJO采用Kryo序列化，不涉及到POJO的具体结构，具备可扩展性，压缩效率高

- 官方的生产者消费者示例
    - **[TODO]**

## 相关连接
- [kryo - QuickStart](https://github.com/EsotericSoftware/kryo#quickstart)
- Kafka
    - [kafka 官方文档](http://kafka.apache.org/documentation.html#introduction)
        - [kafka 安装使用](http://kafka.apache.org/documentation.html#quickstart)
    - [kafka Example](https://github.com/apache/kafka/tree/trunk/examples/src/main/java/kafka/examples) 