package com.gpcoder.directexchange;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.directexchange.Constant.*;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException {
        Producer devProducer = new Producer(DEV_QUEUE_NAME, DEV_ROUTING_KEY);
        devProducer.start();
        devProducer.send("This message for all developers");

        Producer managerProducer = new Producer(MANAGER_QUEUE_NAME, MANAGER_ROUTING_KEY);
        managerProducer.start();
        managerProducer.send("This message for all managers");

        Producer generalProducer = new Producer(GENERAL_QUEUE_NAME, GENERAL_ROUTING_KEY);
        generalProducer.start();
        generalProducer.send("This message for everyone");

        Consumer devConsumer = new Consumer(DEV_QUEUE_NAME, DEV_ROUTING_KEY);
        devConsumer.start();
        devConsumer.subscribe();

        Consumer managerConsumer = new Consumer(MANAGER_QUEUE_NAME, MANAGER_ROUTING_KEY);
        managerConsumer.start();
        managerConsumer.subscribe();

        Consumer generalConsumer = new Consumer(GENERAL_QUEUE_NAME, GENERAL_ROUTING_KEY);
        generalConsumer.start();
        generalConsumer.subscribe();
    }
}
