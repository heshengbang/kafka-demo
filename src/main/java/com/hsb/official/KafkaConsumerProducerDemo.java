package com.hsb.official;

/**
 * Blog: https://www.heshengbang.tech
 * Twitter: https://twitter.com/heshengbang
 * Github: https://github.com/heshengbang
 * Time: 2019/1/7 15:23
 *
 * @author heshengbang
 */

public class KafkaConsumerProducerDemo {
    public static void main(String[] args) {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        PeopleProducer producerThread = new PeopleProducer(KafkaProperties.TOPIC, isAsync);
        producerThread.start();
        PeopleConsumer consumerThread = new PeopleConsumer(KafkaProperties.TOPIC);
        consumerThread.start();
    }
}