package com.gpcoder.topicexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.topicexchange.Constant.EXCHANGE_NAME;

public class Consumer {

    private String queueName;
    private String routingKey;
    private TopicExchangeChannel channel;

    public Consumer(String queueName, String routingKey) {
        this.queueName = queueName;
        this.routingKey = routingKey;
    }

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new TopicExchangeChannel(connection, EXCHANGE_NAME);

        // Create topic exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(queueName);

        // Binding queues with routing key
        channel.performQueueBinding(queueName, routingKey);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(queueName);
    }

}
