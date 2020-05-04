package com.gpcoder.topicexchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class TopicExchangeChannel {

    private String exchangeName;
    private Channel channel;
    private Connection connection;

    public TopicExchangeChannel(Connection connection, String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        this.connection = connection;
        this.channel = connection.createChannel();
    }

    public void declareExchange() throws IOException {
        // exchangeDeclare( exchange, builtinExchangeType, durable)
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true);
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
        }), consumerTag -> {
            System.out.println(consumerTag);
        });
    }

    public void publishMessage(String message, String messageKey) throws IOException {
        // basicPublish - ( exchange, routingKey, basicProperties, body)
        System.out.println("[Send] [" + messageKey + "]: " + message);
        channel.basicPublish(exchangeName, messageKey, null, message.getBytes());
    }
}
