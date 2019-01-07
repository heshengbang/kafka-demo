package com.hsb.springboot.config.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import com.hsb.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
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
public class KryoPeopleSerializer implements Serializer<People> {
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
    public byte[] serialize(String topic, People data) {
        try (Output output = new Output(new byte[5 * 1024 * 1024])
        ) {
            kryoLocal.get().register(People.class);
            kryoLocal.get().writeObject(output, data);
            return output.toBytes();
        } catch (Exception e) {
            log.error("序列化失败：", e);
            throw e;
        }
    }

    @Override
    public void close() {
        kryoLocal.remove();
    }
}
