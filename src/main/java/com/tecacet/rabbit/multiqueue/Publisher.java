package com.tecacet.rabbit.multiqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

public class Publisher {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    @SneakyThrows
    public void publish(String message) {
        try (Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()) {

            String routingKey = "my_route";
            channel.basicPublish("my_topic_exchange",
                    routingKey,
                    null, //properties
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
    }

    public static void main(String[] args) {
        new Publisher().publish("Salute!");
    }
}
