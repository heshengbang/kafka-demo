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