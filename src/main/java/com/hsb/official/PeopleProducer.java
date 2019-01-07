package com.hsb.official;

import com.hsb.entity.People;
import com.hsb.springboot.config.serializer.PeopleSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/7 14:45
 *
 * @author heshengbang
 */
@Slf4j
public class PeopleProducer extends Thread {
    private final KafkaProducer<String, Object> producer;
    private final String topic;
    private final Boolean isAsync;

    PeopleProducer(String topic, Boolean isAsync) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PeopleSerializer.class.getName());
        producer = new KafkaProducer<>(props);
        this.topic = topic;
        this.isAsync = isAsync;
    }

    @Override
    public void run() {
        int messageNo = 1;
        int count = 10;
        while (messageNo < count) {
            String message = "Message_" + messageNo;
            long startTime = System.currentTimeMillis();
            People people = new People();
            people.setAge(messageNo);
            people.setBorn(new Date());
            people.setName("people_" + messageNo);
            if (isAsync) {
                // Send asynchronously
                producer.send(new ProducerRecord<>(topic, message, people), new DemoCallBack(startTime, message, people));
            } else {
                // Send synchronously
                try {
                    producer.send(new ProducerRecord<>(topic, message, people)).get();
                    System.out.println("Producer：Sent message: (" + message + ", " + people.toString() + ")");
                } catch (InterruptedException | ExecutionException e) {
                    log.error("发送消息到kafka失败：", e);
                }
            }
            ++messageNo;
        }
        System.out.println("消费者线程结束");
    }
}

@Slf4j
class DemoCallBack implements Callback {

    private final long startTime;
    private final String key;
    private final People people;

    DemoCallBack(long startTime, String key, People people) {
        this.startTime = startTime;
        this.key = key;
        this.people = people;
    }

    /**
     * A callback method the user can implement to provide asynchronous handling of request completion. This method will
     * be called when the record sent to the server has been acknowledged. When exception is not null in the callback,
     * metadata will contain the special -1 value for all fields except for topicPartition, which will be valid.
     *
     * @param metadata  The metadata for the record that was sent (i.e. the partition and offset). Null if an error
     *                  occurred.
     * @param exception The exception thrown during processing of this record. Null if no error occurred.
     */
    @Override
    public void onCompletion(RecordMetadata metadata, Exception exception) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        if (metadata != null) {
            System.out.println(
                    "message(" + key + ", " + people.toString() + ") sent to partition(" + metadata.partition() +
                            "), " +
                            "offset(" + metadata.offset() + ") in " + elapsedTime + " ms");
        } else {
            log.error(exception.getMessage());
        }
    }
}