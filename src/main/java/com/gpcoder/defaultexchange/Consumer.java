package com.gpcoder.defaultexchange;

import com.rabbitmq.client.*;

import java.util.concurrent.TimeUnit;

public class Consumer {

    private final static String QUEUE_NAME = "gpcoder-queue";

    public static void main(String[] argv) throws Exception {
        System.out.println("Create a ConnectionFactory");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        System.out.println("Create a Connection");
        System.out.println("Create a Channel");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        System.out.println("Create a queue " + QUEUE_NAME);
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        System.out.println("Start receiving messages ... ");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received: '" + message + "'");
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(" [-] Done for " + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        CancelCallback cancelCallback = consumerTag -> { };
        String consumerTag = channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
        System.out.println("consumerTag: " + consumerTag);
    }
}
