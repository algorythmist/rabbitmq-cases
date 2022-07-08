package com.tecacet.rabbit.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.tecacet.rabbit.QueueProperties;

import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class Worker {

    private final QueueProperties queueProperties;
    private final ConnectionFactory factory = new ConnectionFactory();

    public void consume() throws Exception {

        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(queueProperties.getQueueName(),
                queueProperties.isDurable(),
                queueProperties.isExclussive(),
                queueProperties.isAutoDelete(), null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                //send acknowledgement
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queueProperties.getQueueName(),
                false, deliverCallback, consumerTag -> { });
    }

    public void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        QueueProperties queueProperties = TaskQueue.taskQueueProperties();
        new Worker(queueProperties).consume();

    }
}
