package com.hsb.springboot.config.deserializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/2 17:09
 *
 * @author heshengbang
 */
@Component
public class KryoStringDeserializer implements Deserializer<String> {

    private final ThreadLocal<Kryo> threadLocal = ThreadLocal.withInitial(Kryo::new);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public String deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        Input input = new Input(data);
        return threadLocal.get().readObject(input, String.class);
    }

    @Override
    public void close() {

    }
}
