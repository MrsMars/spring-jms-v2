package com.aoher.converter;

import com.aoher.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class PersonMessageConverter implements MessageConverter {

    private static final Logger log = LoggerFactory.getLogger(PersonMessageConverter.class);

    private ObjectMapper mapper;

    public PersonMessageConverter() {
        this.mapper = new ObjectMapper();
    }

    @Override
    @NonNull
    public Message toMessage(@NonNull Object object, @NonNull Session session) throws JMSException {
        Person person = (Person) object;

        String payload = null;
        try {
            payload = mapper.writeValueAsString(person);
            log.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            log.error("error converting form person", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    @Override
    @NonNull
    public Object fromMessage(@NonNull Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        String payload = textMessage.getText();
        log.info("inbound json='{}'", payload);

        Person person = null;
        try {
            person = mapper.readValue(payload, Person.class);
        } catch (Exception e) {
            log.error("error converting to person", e);
        }
        return person == null ? new Person() : person;
    }
}
