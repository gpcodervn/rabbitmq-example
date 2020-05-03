package com.gpcoder.fanoutexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.fanoutexchange.Constant.EXCHANGE_NAME;

public class Producer {

    private String queueName;
    private FanoutExchangeChannel channel;

    public Producer(String queueName) {
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

        // Binding queues witho routing key
        channel.performQueueBinding(queueName);
    }

    public void send(String message) throws IOException {
        // Send message
        channel.publishMessage(message);
    }
}
