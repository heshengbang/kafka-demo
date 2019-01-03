package com.hsb.springboot.config.deserializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.hsb.springboot.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * @author heshengbang
 * @date 2019/1/2
 * https://github.com/heshengbang
 * www.heshengbang.men
 * email: trulyheshengbang@gmail.com
 */
@Component
@Slf4j
public class KryoPeopleDeserializer implements Deserializer<People> {

    /**
     * 由于kryo不是线程安全的，所以每个线程都使用独立的kryo
     */
    private final ThreadLocal<Kryo> kryoLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(People.class, new BeanSerializer<>(kryo, People.class));
        return kryo;
    });

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public People deserialize(String topic, byte[] data) {
        try {
            if (data == null || data.length == 0) {
                log.error("反序列化失败，字节数组为空");
                return null;
            }
            Input input = new Input(data);
            Kryo kryo = kryoLocal.get();
            kryo.register(People.class);
            return kryo.readObject(input, People.class);
        } catch (Exception e) {
            log.error("反序列化失败：", e);
            throw e;
        }
    }

    @Override
    public void close() {
        kryoLocal.remove();
    }
}
