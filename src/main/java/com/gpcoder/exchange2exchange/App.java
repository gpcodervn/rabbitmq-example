package com.gpcoder.exchange2exchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.exchange2exchange.Constant.DESIGN_PATTERN_MSG_KEY;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        HeadersExchangeProducer producer1 = new HeadersExchangeProducer();
        producer1.start();

        TopicExchangeProducer producer2 = new TopicExchangeProducer();
        producer2.start();

        // Publish some messages
        Map<String, Object> devHeader = new HashMap<>();
        devHeader.put("dev", "Developer Channel");
        producer2.send("[1] Head First Design Pattern", DESIGN_PATTERN_MSG_KEY, devHeader);

        HeadersExchangeConsumer consumer1 = new HeadersExchangeConsumer();
        consumer1.start();
        consumer1.subscribe();

        TopicExchangeConsumer consumer2 = new TopicExchangeConsumer();
        consumer2.start();
        consumer2.subscribe();
    }
}
