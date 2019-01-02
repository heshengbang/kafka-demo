package com.hsb.springboot;

import com.hsb.springboot.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2018/12/29 9:23
 * @author heshengbang
 */
@Component
@Slf4j
public class ConsumerListener {
    @KafkaListener(topics = {"test1"}, containerFactory = "basicContainerFactory")
    public void listen (ConsumerRecord<String, String> record) {
        System.out.printf("topic = %s, offset = %d, value = %s \n", record.topic(), record.offset(), record.value());
    }

    @KafkaListener(topics = {"people"}, containerFactory = "peopleContainerFactory")
    public void listenPeople(ConsumerRecord<String, People> record) {
        System.out.printf("topic = %s, offset = %d \n", record.topic(), record.offset());
        People people = record.value();
        if (people == null) {
            log.error("反序列化的值为null");
            return;
        }
        System.out.printf("名字 = %s  出生日期 = %s  年龄 = %s \n", people.getName(), people.getBorn(), people.getAge());
    }

    @KafkaListener(topics = {"kryoPeople"}, containerFactory = "kryoPeopleContainerFactory")
    public void listenPeople2(ConsumerRecord<String, People> record) {
        System.out.printf("topic = %s, offset = %d \n", record.topic(), record.offset());
        People people = record.value();
        if (people == null) {
            log.error("反序列化的值为null");
            return;
        }
        System.out.printf("名字 = %s  出生日期 = %s  年龄 = %s \n", people.getName(), people.getBorn(), people.getAge());
    }
}
