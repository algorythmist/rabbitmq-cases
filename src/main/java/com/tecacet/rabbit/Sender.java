package com.tecacet.rabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class Sender {

    private final QueueProperties queueProperties;
    private final ConnectionFactory factory = new ConnectionFactory();

    @SneakyThrows
    public void send(String message) {
        //factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.queueDeclare(queueProperties.getQueueName(),
                    queueProperties.isDurable(),
                    queueProperties.isExclusive(),
                    queueProperties.isAutoDelete(), null);
            channel.basicPublish(queueProperties.getExchange(),
                    queueProperties.getQueueName(),
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

    public static void main(String[] argv) throws Exception {
        new Sender(new QueueProperties()).send("Hello my friend!");

    }
}
