package com.gpcoder.fanoutexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.fanoutexchange.Constant.EXCHANGE_NAME;

public class Consumer {

    private String queueName;
    private FanoutExchangeChannel channel;

    public Consumer(String queueName) {
        this.queueName = queueName;
    }

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new FanoutExchangeChannel(connection, EXCHANGE_NAME);

        // Create fanout exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(queueName);

        // Binding queues without routing key
        channel.performQueueBinding(queueName);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(queueName);
    }

}
