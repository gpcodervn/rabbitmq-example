package com.gpcoder.directexchange;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.directexchange.Constant.*;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        // Create producer
        Producer producer = new Producer();
        producer.start();

        // Publish some message
        producer.send("This message for all developers", DEV_ROUTING_KEY);
        producer.send("This message for all managers", MANAGER_ROUTING_KEY);
        producer.send("This message for everyone", GENERAL_ROUTING_KEY);

        Consumer consumer = new Consumer();
        consumer.start();
        consumer.subscribe();
    }
}
