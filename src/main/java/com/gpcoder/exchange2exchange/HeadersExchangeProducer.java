package com.gpcoder.exchange2exchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.exchange2exchange.Constant.*;

public class HeadersExchangeProducer {

    private ExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new ExchangeChannel(connection, HEADERS_EXCHANGE_NAME);

        // Create headers exchange
        channel.declareExchange(BuiltinExchangeType.HEADERS);

        // Create queues
        channel.declareQueues(DEV_QUEUE_NAME, MANAGER_QUEUE_NAME, PUBLISHED_QUEUE_NAME);

        // Binding queues with headers
        Map<String, Object> devGroup = new HashMap<>();
        devGroup.put("x-match", "any"); // Match any of the header
        devGroup.put("dev", "Developer Channel");
        devGroup.put("general", "General Channel");

        Map<String, Object> managerGroup = new HashMap<>();
        managerGroup.put("x-match", "any"); // Match any of the header
        managerGroup.put("dev", "Developer Channel");
        managerGroup.put("manager", "Manager Channel");
        managerGroup.put("general", "General Channel");

        Map<String, Object> publishedGroup = new HashMap<>();
        publishedGroup.put("x-match", "all"); // Match all of the header
        publishedGroup.put("general", "General Channel");
        publishedGroup.put("access", "publish");

        channel.performQueueBinding(DEV_QUEUE_NAME, "", devGroup);
        channel.performQueueBinding(MANAGER_QUEUE_NAME, "", managerGroup);
        channel.performQueueBinding(PUBLISHED_QUEUE_NAME, "", publishedGroup);
    }
}
