package com.gpcoder.fanoutexchange;

        import java.io.IOException;
        import java.util.concurrent.TimeUnit;
        import java.util.concurrent.TimeoutException;

public class App {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // Create producer
        Producer producer = new Producer();
        producer.start();

        // Send one message to exchange, it will be forwarded to all queues
        producer.send("gpcoder message 1");

        // Create consumer
        Consumer consumer = new Consumer();
        consumer.start();
        consumer.subscribe();

        TimeUnit.SECONDS.sleep(1);

        // Try to send a second message
        producer.send("gpcoder message 2");
    }
}
