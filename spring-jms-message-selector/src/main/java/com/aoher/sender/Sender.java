package com.aoher.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.aoher.util.Constants.PRIORITY_PROPERTY;
import static com.aoher.util.Priority.HIGH;
import static com.aoher.util.Priority.LOW;

@Component
public class Sender {

    private static final Logger log = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, String message, boolean isHighPriority) {
        log.info("sending message='{}' with highPriority='{}'", message, isHighPriority);

        jmsTemplate.convertAndSend(destination, message, messagePostProcessor -> {
            messagePostProcessor.setStringProperty(
                    PRIORITY_PROPERTY,
                    isHighPriority ? HIGH.getLevel() : LOW.getLevel());
            return messagePostProcessor;
        });
    }
}
