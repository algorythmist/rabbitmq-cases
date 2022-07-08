package com.tecacet.rabbit.multiqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@RequiredArgsConstructor
public class Consumer {

    private final ConnectionFactory factory = new ConnectionFactory();
    private final Random random = new Random();

    private final String queue;

    @SneakyThrows
    public void receiveMessages() {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        boolean autoAck = false;
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            //send acknowledgement

            if (random.nextBoolean()) {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.println("Acked message!");
            } else {
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false, true);
                System.out.println("Failed message!");
            }
        };
        channel.basicConsume(queue, autoAck, deliverCallback, consumerTag -> { });
    }
}
