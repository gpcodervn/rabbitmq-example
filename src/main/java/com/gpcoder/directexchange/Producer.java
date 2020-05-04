package com.gpcoder.directexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.directexchange.Constant.*;

public class Producer {
    private DirectExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new DirectExchangeChannel(connection, EXCHANGE_NAME);

        // Create direct exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(DEV_QUEUE_NAME, MANAGER_QUEUE_NAME, GENERAL_QUEUE_NAME);

        // Binding queues with routing keys
        channel.performQueueBinding(DEV_QUEUE_NAME, DEV_ROUTING_KEY);
        channel.performQueueBinding(MANAGER_QUEUE_NAME, MANAGER_ROUTING_KEY);
        channel.performQueueBinding(GENERAL_QUEUE_NAME, GENERAL_ROUTING_KEY);
    }

    public void send(String message, String routingKey) throws IOException {
        // Send message
        channel.publishMessage(message, routingKey);
    }
}
