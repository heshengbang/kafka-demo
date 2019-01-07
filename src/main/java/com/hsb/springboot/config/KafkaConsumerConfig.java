package com.hsb.springboot.config;

import com.hsb.springboot.config.deserializer.KryoPeopleDeserializer;
import com.hsb.springboot.config.deserializer.KryoStringDeserializer;
import com.hsb.springboot.config.deserializer.PeopleDeserializer;
import com.hsb.entity.People;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/2 15:52
 *
 * @author heshengbang
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, People> kryoPeopleContainerFactory() {
        Map<String, Object> configProps = new HashMap<>(8);
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");

        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KryoPeopleDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");

        ConcurrentKafkaListenerContainerFactory<String, People> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, People> peopleConsumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        configProps,
                        new StringDeserializer(),
                        new KryoPeopleDeserializer());
        factory.setConsumerFactory(peopleConsumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, People> peopleContainerFactory() {
        Map<String, Object> configProps = new HashMap<>(8);
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");

        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PeopleDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");

        ConcurrentKafkaListenerContainerFactory<String, People> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, People> peopleConsumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        configProps,
                        new StringDeserializer(),
                        new PeopleDeserializer());
        factory.setConsumerFactory(peopleConsumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> basicContainerFactory() {
        Map<String, Object> configProps = new HashMap<>(8);
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        configProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "500");
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");

        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KryoStringDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, String> peopleConsumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        configProps,
                        new StringDeserializer(),
                        new KryoStringDeserializer());
        factory.setConsumerFactory(peopleConsumerFactory);
        return factory;
    }
}
