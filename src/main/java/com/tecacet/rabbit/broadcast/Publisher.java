package com.tecacet.rabbit.broadcast;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.tecacet.rabbit.QueueProperties;

import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Publisher {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final QueueProperties queueProperties;

    public Publisher(QueueProperties queueProperties) {
        this.queueProperties = queueProperties;
    }

    @SneakyThrows
    public void publish(Message message) {
        try(Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()){
            channel.exchangeDeclare(queueProperties.getExchange(),
                    queueProperties.getExchangeType().getWord());

            String routingKey = message.getSeverity().name();
            channel.basicPublish(queueProperties.getExchange(),
                    routingKey,
                    null, //properties
                    message.getMessage().getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        Publisher publisher = new Publisher(LogExchange.exchangeProperties());
        List<Message> messages = Arrays.asList(
                new Message(Message.Severity.ERROR, "YAAK!"),
                new Message(Message.Severity.WARNING, "Beware!"),
                new Message(Message.Severity.INFO, "Just sayin"));
        messages.forEach(publisher::publish);
    }
}
