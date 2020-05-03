package com.gpcoder.headersexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.headersexchange.Constant.EXCHANGE_NAME;

public class Consumer {

    private String queueName;
    private Map<String, Object> headers;
    private HeadersExchangeChannel channel;

    public Consumer(String queueName, Map<String, Object> headers) {
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

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(queueName);
    }

}
