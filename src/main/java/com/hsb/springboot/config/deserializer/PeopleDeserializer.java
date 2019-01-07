package com.hsb.springboot.config.deserializer;

import com.hsb.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/2 16:01
 *
 * @author heshengbang
 */
@Component
@Slf4j
public class PeopleDeserializer implements Deserializer<People> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public People deserialize(String topic, byte[] data) {
        People readObject = null;
        try (ByteArrayInputStream in = new ByteArrayInputStream(data);
             ObjectInputStream inputStream = new ObjectInputStream(in)){
            readObject = (People) inputStream.readObject();
        } catch (Exception e) {
            log.error("反序列化失败：", e);
        }
        return readObject;
    }

    @Override
    public void close() {
    }
}
