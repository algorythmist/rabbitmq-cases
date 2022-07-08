package com.tecacet.rabbit.broadcast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Message {

    public enum Severity {
        INFO, WARNING, ERROR
    }

    private Severity severity;
    private String message;

}
