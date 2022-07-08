package com.tecacet.rabbit.broadcast;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class WarningReceiver {

    public static void main(String[] args) throws IOException, TimeoutException {
        new DirectReceiver(LogExchange.exchangeProperties(), Message.Severity.WARNING).receive();
    }
}
