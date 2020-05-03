package com.gpcoder.alternateexchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.alternateexchange.Constant.*;

public class TopicExchangeConsumer {

    private ExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new ExchangeChannel(connection, TOPIC_EXCHANGE_NAME);

        // Create topic exchange
        channel.declareExchangeWithAlternateExchagne(BuiltinExchangeType.TOPIC, ALTERNATE_EXCHANGE_NAME);

        // Create queues
        channel.declareQueues(JAVA_QUEUE_NAME, ALL_QUEUE_NAME);

        // Binding queues
        channel.performQueueBinding(JAVA_QUEUE_NAME, JAVA_ROUTING_KEY);
        channel.performQueueBinding(ALL_QUEUE_NAME, GPCODER_ROUTING_KEY);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(JAVA_QUEUE_NAME);
        channel.subscribeMessage(ALL_QUEUE_NAME);
    }
}
