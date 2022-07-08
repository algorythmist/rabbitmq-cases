package com.tecacet.rabbit;

public enum ExchangeType {

    FANOUT("fanout"),
    DIRECT("direct"),
    TOPIC("topic");

    private final String word;

    ExchangeType(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
