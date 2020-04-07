package com.aoher.receiver;

import com.aoher.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "${queue.boot}")
    public void receive(Person person) {
        log.info("received person ='{}'", person);
        latch.countDown();
    }
}
