package com.tecacet.rabbit.worker;

import com.tecacet.rabbit.Sender;

import java.util.Arrays;
import java.util.List;

public class TaskDistributor {

    public static void main(String[] argv) throws Exception {
        List<String> messages = Arrays.asList("let.me.go", "be.that.way", "bread.and.butter");
        new TaskDistributor().distribute(messages);
    }

    public void distribute(List<String> messages) {
        Sender sender = new Sender(TaskQueue.taskQueueProperties());
        messages.forEach(sender::send);
    }
}
