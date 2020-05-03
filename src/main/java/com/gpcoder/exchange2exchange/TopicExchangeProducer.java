package com.gpcoder.exchange2exchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.exchange2exchange.Constant.*;

public class TopicExchangeProducer {

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
        channel.performQueueBinding(DEV_QUEUE_NAME, JAVA_ROUTING_KEY, null);
        channel.performQueueBinding(MANAGER_QUEUE_NAME, GPCODER_ROUTING_KEY, null);

        // Binding headers exchange to topic exchange
        channel.performExchangeBinding(HEADERS_EXCHANGE_NAME, TOPIC_EXCHANGE_NAME, GPCODER_ROUTING_KEY);
    }

    public void send(String message, String messageKey, Map<String, Object> headers) throws IOException {
        // Send message
        channel.publishMessage(message, messageKey, headers);
    }
}
