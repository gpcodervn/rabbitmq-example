package com.gpcoder.headersexchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        // Create producers, queues and binding queues to Headers Exchange
        Producer producer = new Producer();
        producer.start();

        // Publish some messages
        Map<String, Object> devHeader = new HashMap<>();
        devHeader.put("dev", "Developer Channel");
        producer.send("[1] Developer message", devHeader);

        Map<String, Object> managerHeader = new HashMap<>();
        managerHeader.put("manager", "Manager Channel");
        producer.send("[2] Manager message", managerHeader);

        Map<String, Object> generalHeader = new HashMap<>();
        generalHeader.put("general", "General Channel");
        producer.send("[3] General message", generalHeader);

        Map<String, Object> publishedHeader = new HashMap<>();
        publishedHeader.put("general", "General Channel");
        publishedHeader.put("access", "publish");
        producer.send("[4] Published message", publishedHeader);


        // Create consumers, queues and binding queues to Headers Exchange
        Consumer consumer = new Consumer();
        consumer.start();
        consumer.subscribe();
    }
}
