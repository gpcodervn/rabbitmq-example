package com.gpcoder.exchange2exchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.exchange2exchange.Constant.*;
import static com.gpcoder.exchange2exchange.Constant.PUBLISHED_QUEUE_NAME;

public class TopicExchangeConsumer {

    private ExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new ExchangeChannel(connection, TOPIC_EXCHANGE_NAME);

        // Create topic exchange
        channel.declareExchange(BuiltinExchangeType.TOPIC);

        // Create queues
        channel.declareQueues(JAVA_QUEUE_NAME, ALL_QUEUE_NAME);

        // Binding queues without headers
        channel.performQueueBinding(JAVA_QUEUE_NAME, JAVA_ROUTING_KEY, null);
        channel.performQueueBinding(ALL_QUEUE_NAME, GPCODER_ROUTING_KEY, null);

        // Binding headers exchange to topic exchange
        channel.performExchangeBinding(HEADERS_EXCHANGE_NAME, TOPIC_EXCHANGE_NAME, GPCODER_ROUTING_KEY);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(JAVA_QUEUE_NAME);
        channel.subscribeMessage(ALL_QUEUE_NAME);
    }
}
