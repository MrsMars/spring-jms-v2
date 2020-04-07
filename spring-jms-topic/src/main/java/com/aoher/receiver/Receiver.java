package com.aoher.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(2);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${destination.topic}")
    public void receive1(String message) {
        log.info("'subscriber1' received message='{}'", message);
        latch.countDown();
    }

    @JmsListener(destination = "${destination.topic}")
    public void receive2(String message) {
        log.info("'subscriber2' received message='{}'", message);
        latch.countDown();
    }
}
