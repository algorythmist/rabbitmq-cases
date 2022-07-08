package com.tecacet.rabbit.worker;

import com.tecacet.rabbit.QueueProperties;

public class TaskQueue {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static QueueProperties taskQueueProperties() {
        return QueueProperties.builder()
                .queueName(TASK_QUEUE_NAME)
                .durable(true)
                .exclussive(false)
                .autoDelete(false)
                .exchange("")
                .routingKey(TASK_QUEUE_NAME)
                .build();
    }
}
