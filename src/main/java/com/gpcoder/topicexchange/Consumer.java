package com.gpcoder.topicexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.topicexchange.Constant.*;

public class Consumer {

    private TopicExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new TopicExchangeChannel(connection, EXCHANGE_NAME);

        // Create topic exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(JAVA_QUEUE_NAME, GENERAL_QUEUE_NAME);

        // Binding queues with routing key
        channel.performQueueBinding(JAVA_QUEUE_NAME, JAVA_ROUTING_KEY);
        channel.performQueueBinding(GENERAL_QUEUE_NAME, GPCODER_ROUTING_KEY);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(JAVA_QUEUE_NAME);
        channel.subscribeMessage(GENERAL_QUEUE_NAME);
    }

}
