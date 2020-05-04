package com.gpcoder.headersexchange;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.headersexchange.Constant.*;

public class Consumer {

    private HeadersExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new HeadersExchangeChannel(connection, EXCHANGE_NAME);

        // Create headers exchange
        channel.declareExchange();

        // Create headers
        Map<String, Object> devHeaders = new HashMap<>();
        devHeaders.put("x-match", "any"); // Match any of the header
        devHeaders.put("dev", "Developer Channel");
        devHeaders.put("general", "General Channel");

        Map<String, Object> managerHeaders = new HashMap<>();
        managerHeaders.put("x-match", "any"); // Match any of the header
        managerHeaders.put("dev", "Developer Channel");
        managerHeaders.put("manager", "Manager Channel");
        managerHeaders.put("general", "General Channel");

        Map<String, Object> publishedHeaders = new HashMap<>();
        publishedHeaders.put("x-match", "all"); // Match all of the header
        publishedHeaders.put("general", "General Channel");
        publishedHeaders.put("access", "publish");

        // Create queues
        channel.declareQueues(DEV_QUEUE_NAME, MANAGER_QUEUE_NAME, PUBLISHED_QUEUE_NAME);

        // Binding queues with headers
        channel.performQueueBinding(DEV_QUEUE_NAME, devHeaders);
        channel.performQueueBinding(MANAGER_QUEUE_NAME, managerHeaders);
        channel.performQueueBinding(PUBLISHED_QUEUE_NAME, publishedHeaders);
    }

    public void subscribe() throws IOException {
        // Subscribe message
        channel.subscribeMessage(DEV_QUEUE_NAME);
        channel.subscribeMessage(MANAGER_QUEUE_NAME);
        channel.subscribeMessage(PUBLISHED_QUEUE_NAME);
    }

}
