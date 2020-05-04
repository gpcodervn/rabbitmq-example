package com.gpcoder.topicexchange;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.topicexchange.Constant.*;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        // Create producers, queues and binding queues to Topic Exchange
        Producer producer = new Producer();
        producer.start();

        // Publish some message
        producer.send("[1] A new Java Core topic is published", JAVA_CORE_MSG_KEY);
        producer.send("[2] A new Java general topic is published", JAVA_MSG_KEY);
        producer.send("[3] A new Design Pattern topic is published", DESIGN_PATTERN_MSG_KEY);
        producer.send("[4] Not matching any routing key", NOT_MATCHING_MSG_KEY);

        // Create consumers, queues and binding queues to Topic Exchange
        Consumer consumer = new Consumer();
        consumer.start();
        consumer.subscribe();
    }
}
