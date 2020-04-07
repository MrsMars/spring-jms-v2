package com.aoher.sender;

import com.aoher.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    private static final Logger log = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(String destination, Person person) {
        log.info("sending person = '{}' to destination = '{}'", person, destination);
        jmsTemplate.convertAndSend(destination, person);
    }
}
