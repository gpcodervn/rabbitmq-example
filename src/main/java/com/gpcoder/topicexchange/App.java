package com.gpcoder.topicexchange;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.topicexchange.Constant.*;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        // Create producers, queues and binding queues to Topic Exchange
        Producer javaProducer = new Producer(JAVA_QUEUE_NAME, JAVA_ROUTING_KEY);
        javaProducer.start();

        Producer generalProducer = new Producer(GENERAL_QUEUE_NAME, GPCODER_ROUTING_KEY);
        generalProducer.start();

        // Publish some message
        javaProducer.send("[1] A new Java Core topic is published", JAVA_CORE_MSG_KEY);
        generalProducer.send("[2] A new Java general topic is published", JAVA_MSG_KEY);
        generalProducer.send("[3] A new Design Pattern topic is published", DESIGN_PATTERN_MSG_KEY);
        generalProducer.send("[4] Not matching any routing key", NOT_MATCHING_MSG_KEY);

        // Create consumers, queues and binding queues to Topic Exchange
//        Consumer javaConsumer = new Consumer(JAVA_QUEUE_NAME, JAVA_ROUTING_KEY);
//        javaConsumer.start();
//
//        Consumer generalConsumer = new Consumer(GENERAL_QUEUE_NAME, GPCODER_ROUTING_KEY);
//        generalConsumer.start();
//
//        // Subscribe to receive messages
//        javaConsumer.subscribe();
//        generalConsumer.subscribe();
    }
}
