package com.gpcoder.directexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.directexchange.Constant.EXCHANGE_NAME;

public class Consumer {

    private String queueName;
    private String routingKey;
    private DirectExchangeChannel channel;

    public Consumer(String queueName, String routingKey) {
        this.queueName = queueName;
        this.routingKey = routingKey;
    }

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new DirectExchangeChannel(connection, EXCHANGE_NAME);

        // Create direct exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(queueName);

        // Binding queues with routing keys
        channel.performQueueBinding(queueName, routingKey);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(queueName);
    }

}
