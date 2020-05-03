package com.gpcoder.directexchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DirectExchangeChannel {

    private String exchangeName;
    private Channel channel;
    private Connection connection;

    public DirectExchangeChannel(Connection connection, String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        this.connection = connection;
        this.channel = connection.createChannel();
    }

    public void declareExchange() throws IOException {
        // exchangeDeclare( exchange, builtinExchangeType, durable)
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
    }

    public void declareQueues(String ...queueNames) throws IOException {
        for (String queueName : queueNames) {
            // queueDeclare  - (queueName, durable, exclusive, autoDelete, arguments)
            channel.queueDeclare(queueName, true, false, false, null);
        }
    }

    public void performQueueBinding(String queueName, String routingKey) throws IOException {
        // Create bindings - (queue, exchange, routingKey)
        channel.queueBind(queueName, exchangeName, routingKey);
    }

    public void subscribeMessage(String queueName) throws IOException {
        // basicConsume - ( queue, autoAck, deliverCallback, cancelCallback)
        channel.basicConsume(queueName, true, ((consumerTag, message) -> {
            System.out.println("[Received] [" + queueName + "]: " + consumerTag);
            System.out.println("[Received] [" + queueName + "]: " + new String(message.getBody()));

            // Release right after received one message
            // release();
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
    }

    public void publishMessage(String message, String routingKey) throws IOException {
        // basicPublish - ( exchange, routingKey, basicProperties, body)
        System.out.println("[Send] [" + routingKey + "]: " + message);
        channel.basicPublish(exchangeName, routingKey, null, message.getBytes());

        // Release right after published one message
        // release();
    }

    public void release() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
