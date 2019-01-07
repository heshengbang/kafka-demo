package com.hsb.official;

import com.hsb.entity.People;
import com.hsb.springboot.config.deserializer.PeopleDeserializer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/7 14:55
 *
 * @author heshengbang
 */

public class PeopleConsumer extends ShutdownableThread {
    private final KafkaConsumer<String, People> consumer;
    private final String topic;

    PeopleConsumer(String topic) {
        super("PeopleConsumer", false);
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PeopleDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    @Override
    public void doWork() {
        consumer.subscribe(Collections.singletonList(this.topic));
        ConsumerRecords<String, People> records = consumer.poll(Duration.ofSeconds(1L).getSeconds());
        for (ConsumerRecord<String, People> record : records) {
            if (record.value() != null) {
                System.out.println("Consumer：Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
            }
        }
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public boolean isInterruptible() {
        return false;
    }
}
