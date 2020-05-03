package com.gpcoder.alternateexchange;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.alternateexchange.Constant.JAVA_MSG_KEY;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        AlternativeExchangeProducer producer1 = new AlternativeExchangeProducer();
        producer1.start();

        TopicExchangeProducer producer2 = new TopicExchangeProducer();
        producer2.start();

        // Publish some messages
        producer2.send("[1] Head First Design Pattern", JAVA_MSG_KEY);
        producer2.send("[2] Unknown Message", "random-gpcoder");

        AlternativeExchangeConsumer consumer1 = new AlternativeExchangeConsumer();
        consumer1.start();
        consumer1.subscribe();

        TopicExchangeConsumer consumer2 = new TopicExchangeConsumer();
        consumer2.start();
        consumer2.subscribe();
    }
}
