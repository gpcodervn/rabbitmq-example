package com.gpcoder.exchange2exchange;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Map;

public class ExchangeChannel {

    private String exchangeName;
    private Channel channel;
    private Connection connection;

    public ExchangeChannel(Connection connection, String exchangeName) throws IOException {
        this.exchangeName = exchangeName;
        this.connection = connection;
        this.channel = connection.createChannel();
    }

    public void declareExchange(BuiltinExchangeType exchangeType) throws IOException {
        // exchangeDeclare( exchange, builtinExchangeType, durable)
        channel.exchangeDeclare(exchangeName, exchangeType, true);
    }

    public void declareQueues(String ...queueNames) throws IOException {
        for (String queueName : queueNames) {
            // queueDeclare  - (queueName, durable, exclusive, autoDelete, arguments)
            channel.queueDeclare(queueName, true, false, false, null);
        }
    }

    public void performQueueBinding(String queueName, String routingKey, Map<String, Object> headers) throws IOException {
        // Create bindings - (queue, exchange, routingKey, headers)
        channel.queueBind(queueName, exchangeName, routingKey, headers);
    }

    public void performExchangeBinding(String destination, String source, String routingKey) throws IOException {
        // (destination-exchange, source-exchange , routingKey
        channel.exchangeBind(destination, source, routingKey);
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

    public void publishMessage(String message, String routingKey, Map<String, Object> headers) throws IOException {
        BasicProperties properties = new BasicProperties()
                .builder().headers(headers).build();

        // basicPublish - ( exchange, routingKey, basicProperties, body)
        System.out.println("[Send] [" + headers + "]: " + message);
        channel.basicPublish(exchangeName, routingKey, properties, message.getBytes());
    }
}
