package com.gpcoder.fanoutexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.fanoutexchange.Constant.*;

public class Consumer {

    private FanoutExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new FanoutExchangeChannel(connection, EXCHANGE_NAME);

        // Create fanout exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(DEV_QUEUE_NAME, MANAGER_QUEUE_NAME, GENERAL_QUEUE_NAME);

        // Binding queues without routing key
        channel.performQueueBinding(DEV_QUEUE_NAME);
        channel.performQueueBinding(MANAGER_QUEUE_NAME);
        channel.performQueueBinding(GENERAL_QUEUE_NAME);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(DEV_QUEUE_NAME);
        channel.subscribeMessage(MANAGER_QUEUE_NAME);
        channel.subscribeMessage(GENERAL_QUEUE_NAME);
    }

}
