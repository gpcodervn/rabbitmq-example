package com.gpcoder.headersexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.headersexchange.Constant.EXCHANGE_NAME;

public class Producer {

    private String queueName;
    private Map<String, Object> headers;
    private HeadersExchangeChannel channel;

    public Producer(String queueName, Map<String, Object> headers) {
        this.queueName = queueName;
        this.headers = headers;
    }

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new HeadersExchangeChannel(connection, EXCHANGE_NAME);

        // Create headers exchange
        channel.declareExchange();

        // Create queues
        channel.declareQueues(queueName);

        // Binding queues with headers
        channel.performQueueBinding(queueName, headers);
    }

    public void send(String message, Map<String, Object> headers) throws IOException {
        // Send message
        channel.publishMessage(message, headers);
    }
}
