package com.gpcoder.headersexchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.headersexchange.Constant.*;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        // Create headers

        Map<String, Object> devGroup = new HashMap<>();
        devGroup.put("x-match", "any"); // Match any of the header
        devGroup.put("dev", "Developer Channel");
        devGroup.put("general", "General Channel");

        Map<String, Object> managerGroup = new HashMap<>();
        managerGroup.put("x-match", "any"); // Match any of the header
        managerGroup.put("dev", "Developer Channel");
        managerGroup.put("manager", "Manager Channel");
        managerGroup.put("general", "General Channel");

        Map<String, Object> publishedGroup = new HashMap<>();
        publishedGroup.put("x-match", "all"); // Match all of the header
        publishedGroup.put("general", "General Channel");
        publishedGroup.put("access", "publish");

        // Create producers, queues and binding queues to Headers Exchange
        Producer devProducer = new Producer(DEV_QUEUE_NAME, devGroup);
        devProducer.start();

        Producer managerProducer = new Producer(MANAGER_QUEUE_NAME, managerGroup);
        managerProducer.start();

        Producer generalProducer = new Producer(PUBLISHED_QUEUE_NAME, publishedGroup);
        generalProducer.start();

        // Publish some messages
        Map<String, Object> devHeader = new HashMap<>();
        devHeader.put("dev", "Developer Channel");
        devProducer.send("[1] Developer message", devHeader);

        Map<String, Object> managerHeader = new HashMap<>();
        managerHeader.put("manager", "Manager Channel");
        managerProducer.send("[2] Manager message", managerHeader);

        Map<String, Object> generalHeader = new HashMap<>();
        generalHeader.put("general", "General Channel");
        generalProducer.send("[3] General message", generalHeader);

        Map<String, Object> publishedHeader = new HashMap<>();
        publishedHeader.put("general", "General Channel");
        publishedHeader.put("access", "publish");
        generalProducer.send("[4] Published message", publishedHeader);


        // Create consumers, queues and binding queues to Headers Exchange
        Consumer developer = new Consumer(DEV_QUEUE_NAME, devGroup);
        developer.start();
        developer.subscribe();

        Consumer manager = new Consumer(MANAGER_QUEUE_NAME, managerGroup);
        manager.start();
        manager.subscribe();

        Consumer customer = new Consumer(PUBLISHED_QUEUE_NAME, publishedGroup);
        customer.start();
        customer.subscribe();
    }
}
