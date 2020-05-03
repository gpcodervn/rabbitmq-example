package com.gpcoder.deadletterexchange;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class App {

    // Exchange
    private static final String WORK_EXCHANGE_NAME = "GPcoder.WorkExchange";
    private static final String RETRY_EXCHANGE_NAME = "GPcoder.RetryExchange";

    // Queue
    private static final String WORK_QUEUE_NAME = "WorkQueue";
    private static final String RETRY_QUEUE_NAME = "RetryQueue";

    private static final int RETRY_DELAY = 300; // in ms

    private static Channel channel;
    private static int RETRY_COUNT = 0;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        channel = connection.createChannel();

        // Create the WorkQueue
        channel.exchangeDeclare(WORK_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(WORK_QUEUE_NAME, true, false, false, null);
        channel.queueBind(WORK_QUEUE_NAME, WORK_EXCHANGE_NAME, "", null);

        // Create the RetryQueue
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", WORK_EXCHANGE_NAME);
        arguments.put("x-message-ttl", RETRY_DELAY);
        channel.exchangeDeclare(RETRY_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(RETRY_QUEUE_NAME, true, false, false, arguments);
        channel.queueBind(RETRY_QUEUE_NAME, RETRY_EXCHANGE_NAME, "", null);

        // basicPublish - ( exchange, routingKey, basicProperties, body)
        String message = "GPCoder Message";
        System.out.println("[" + LocalDateTime.now() + "] [Work] [Send]: " + message);
        channel.basicPublish(WORK_EXCHANGE_NAME, "", null, message.getBytes());

        consumer(WORK_QUEUE_NAME);
    }

    private static void consumer(String queueName) throws IOException {
        // basicConsume - ( queue, autoAck, deliverCallback, cancelCallback)
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String content = new String(message.getBody());
            System.out.println("[" + LocalDateTime.now() + "] [Received] [" + queueName + "]: " + content);
            System.out.println("");
            if (RETRY_COUNT < 5) {
                publishToRetryExchange(content);
                RETRY_COUNT++;
            } else {
                RETRY_COUNT = 0;
            }
        };
        CancelCallback cancelCallback = consumerTag -> System.out.println(consumerTag);
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }

    // Publish to RetryQueue on failure
    private static void publishToRetryExchange(String message) throws IOException {
        System.out.println("[" + LocalDateTime.now() + "] [Retry" + RETRY_COUNT + "] [Re-Publish]: " + message);
        channel.basicPublish(RETRY_EXCHANGE_NAME, "", null, message.getBytes());
    }
}
