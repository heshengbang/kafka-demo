package com.hsb.official.web;

import com.hsb.entity.People;
import com.hsb.official.KafkaProperties;
import com.hsb.official.simple.PeopleConsumer;
import com.hsb.springboot.config.serializer.PeopleSerializer;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author heshengbang
 * @date 2019/1/7
 * https://github.com/heshengbang
 * www.heshengbang.men
 * email: trulyheshengbang@gmail.com
 */
@RestController
@Slf4j
public class OfficialKafkaProducerController implements InitializingBean {
    private KafkaProducer<String, Object> producer;
    @Override
    public void afterPropertiesSet() {
        // 创建生产者
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaProperties.PRODUER_CLIENT_ID);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PeopleSerializer.class.getName());
        producer = new KafkaProducer<>(props);

        // 启动消费者
        PeopleConsumer peopleConsumer = new PeopleConsumer(KafkaProperties.TOPIC);
        peopleConsumer.start();
    }

    @GetMapping("/officialKafkaExample")
    public void officialKafkaExample(@RequestParam("topic")String topic, @RequestParam("msg") String msg, @RequestParam("age") int age) {
        People people = new People();
        people.setAge(age);
        people.setBorn(new Date());
        people.setName(msg);
        try {
            RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(topic, msg, people)).get();
            System.out.println("Official_Producer：Sent message: (" + msg + ", " + people.toString() + "), offset = " + recordMetadata.offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            log.error("发送消息到kafka失败：", e);
        }
    }
}
