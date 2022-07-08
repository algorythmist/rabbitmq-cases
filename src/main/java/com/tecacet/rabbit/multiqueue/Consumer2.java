package com.tecacet.rabbit.multiqueue;

public class Consumer2 {

    public static void main(String[] args) {
        new Consumer("my_queue_2").receiveMessages();
    }
}
