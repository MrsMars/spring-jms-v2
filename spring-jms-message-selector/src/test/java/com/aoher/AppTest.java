package com.aoher;

import com.aoher.receiver.Receiver;
import com.aoher.sender.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class AppTest {

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Test
    public void testReceive() throws InterruptedException {
        String destination = "selector.q";

        sender.send(destination, "High priority 1!", true);
        sender.send(destination, "Low priority 1!", false);
        sender.send(destination, "High priority 2!", true);

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertEquals(0, receiver.getLatch().getCount());
    }
}