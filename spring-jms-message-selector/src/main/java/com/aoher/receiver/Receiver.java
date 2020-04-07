package com.aoher.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    private static final String HIGH_PRIORITY = "priority = 'high'";
    private static final String LOW_PRIORITY = "priority = 'low'";

    private CountDownLatch latch = new CountDownLatch(2);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${queue.boot}", selector = HIGH_PRIORITY)
    public void receiveHigh(String message) {
        log.info("received high priority message='{}'", message);
        latch.countDown();
    }

    @JmsListener(destination = "${queue.boot}", selector = LOW_PRIORITY)
    public void receiveLow(String message) {
        log.info("received low priority message='{}'", message);
    }
}