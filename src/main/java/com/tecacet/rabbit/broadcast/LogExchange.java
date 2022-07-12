package com.tecacet.rabbit.broadcast;

import com.tecacet.rabbit.ExchangeType;
import com.tecacet.rabbit.QueueProperties;

public class LogExchange {

    public static QueueProperties exchangeProperties() {
        return QueueProperties.builder()
                .queueName("")
                .durable(true)
                .exclusive(false)
                .autoDelete(false)
                .exchange("direct_logs")
                .exchangeType(ExchangeType.DIRECT)
                .build();
    }
}
