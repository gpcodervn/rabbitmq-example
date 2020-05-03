package com.gpcoder.alternateexchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.gpcoder.alternateexchange.Constant.ALTERNATE_EXCHANGE_NAME;
import static com.gpcoder.alternateexchange.Constant.UNKNOWN_QUEUE_NAME;

public class AlternativeExchangeProducer {

    private ExchangeChannel channel;

    public void start() throws IOException, TimeoutException {
        // Create connection
        Connection connection = ConnectionManager.createConnection();

        // Create channel
        channel = new ExchangeChannel(connection, ALTERNATE_EXCHANGE_NAME);

        // Create fanout exchange
        channel.declareExchange(BuiltinExchangeType.FANOUT);

        // Create queues
        channel.declareQueues(UNKNOWN_QUEUE_NAME);

        channel.performQueueBinding(UNKNOWN_QUEUE_NAME, "");
    }
}
