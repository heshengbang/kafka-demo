package com.hsb.springboot.config.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/2 17:06
 *
 * @author heshengbang
 */
@Component
public class KryoStringSerializer implements Serializer<String> {

    private ThreadLocal<Kryo> threadKryo = ThreadLocal.withInitial(Kryo::new);

    private ThreadLocal<byte[]> threadBuffer = ThreadLocal.withInitial(() -> new byte[5 * 1024 * 1024]);

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, String data) {
        Output output = new Output(threadBuffer.get());
        threadKryo.get().writeObject(output, data);
        return output.toBytes();
    }

    @Override
    public void close() {

    }
}
