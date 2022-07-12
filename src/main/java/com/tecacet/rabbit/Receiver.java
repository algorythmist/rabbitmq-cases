package com.tecacet.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
public class Receiver {

    private final QueueProperties queueProperties;
    private final ConnectionFactory factory = new ConnectionFactory();

    private final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        System.out.println(" [x] Received '" + message + "'");
    };

    public void consume() throws IOException, TimeoutException {
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueProperties.getQueueName(),
                queueProperties.isDurable(),
                queueProperties.isExclusive(),
                queueProperties.isAutoDelete(), null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        boolean autoAck = true;
        channel.basicConsume(queueProperties.getQueueName(),
                autoAck,
                new HashMap<>(), //arguments
                deliverCallback,
                consumerTag -> { }, //cancel callback
                (consumerTag, signal) -> {} //shutdown signal callback
                );
    }

    public static void main(String[] argv) throws Exception {
        new Receiver(new QueueProperties()).consume();
    }

}
