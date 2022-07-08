package com.tecacet.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueProperties {

    private String queueName = "hello";
    private boolean durable = true;
    private boolean exclussive = false;
    private boolean autoDelete = false;

    private String exchange = "";
    private ExchangeType exchangeType;
    private String routingKey = queueName;


}
