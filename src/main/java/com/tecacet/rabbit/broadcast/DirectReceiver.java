package com.tecacet.rabbit.broadcast;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.tecacet.rabbit.QueueProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DirectReceiver {

    private final ConnectionFactory factory = new ConnectionFactory();

    private final QueueProperties queueProperties;
    private final Message.Severity severity;

    public DirectReceiver(QueueProperties queueProperties, Message.Severity severity) {
        this.queueProperties = queueProperties;
        this.severity = severity;
    }

    public void receive() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(queueProperties.getExchange(),
                queueProperties.getExchangeType().getWord());
        String queueName = severity.name() + "_queue";
        channel.queueDeclare(queueName,
                true, //durable
                false, false, null);

        channel.queueBind(queueName, queueProperties.getExchange(), severity.name());
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        boolean autoAck = false;
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            //send acknowledgement
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };

        channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
        });

    }


}
