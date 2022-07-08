package com.tecacet.rabbit.multiqueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Setup {

    private final ConnectionFactory factory = new ConnectionFactory();

    public void setupExchangeAndQueues() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("my_topic_exchange", BuiltinExchangeType.TOPIC,
                    true); //durable

        channel.queueDeclare("my_queue_1",
                true,
                false, false, null);
        channel.queueDeclare("my_queue_2",
                true,
                false, false, null);

        channel.queueBind("my_queue_1", "my_topic_exchange", "my_route");
        channel.queueBind("my_queue_2", "my_topic_exchange", "my_route");
        channel.close();
    }

    public static void main(String[] args) throws Exception {
        new Setup().setupExchangeAndQueues();
    }
}
