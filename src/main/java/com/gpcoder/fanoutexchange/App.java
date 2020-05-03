package com.gpcoder.fanoutexchange;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.fanoutexchange.Constant.*;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // Create 3 producers
        Producer devProducer = new Producer(DEV_QUEUE_NAME);
        Producer managerProducer = new Producer(MANAGER_QUEUE_NAME);

        // Create 3 queues
        devProducer.start();
        managerProducer.start();

        // Send one message to exchange, it will be forwarded to all queues
        devProducer.send("gpcoder message 1");

        // Create 3 consumers
        Consumer devConsumer = new Consumer(DEV_QUEUE_NAME);
        Consumer managerConsumer = new Consumer(MANAGER_QUEUE_NAME);

        // Create 3 queues: in case of we would like to start consumer before starting the producer, this step is necessary
        devConsumer.start();
        managerConsumer.start();

        // Subscribe the message
        devConsumer.subscribe();
        managerConsumer.subscribe();

        TimeUnit.SECONDS.sleep(1);

        // Try to declare a queue and binding after the 1st message published
        Consumer generalConsumer = new Consumer(GENERAL_QUEUE_NAME);
        generalConsumer.start();
        generalConsumer.subscribe();

        TimeUnit.SECONDS.sleep(1);

        // Try to send a second message
        devProducer.send("gpcoder message 2");


        Consumer generalConsumer2 = new Consumer(GENERAL_QUEUE_NAME);
        generalConsumer2.start();
        generalConsumer2.subscribe();
        devProducer.send("gpcoder message 3");
    }
}
