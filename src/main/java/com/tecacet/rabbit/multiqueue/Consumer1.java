package com.tecacet.rabbit.multiqueue;

public class Consumer1 {

    public static void main(String[] args) {
        new Consumer("my_queue_1").receiveMessages();
    }
}
