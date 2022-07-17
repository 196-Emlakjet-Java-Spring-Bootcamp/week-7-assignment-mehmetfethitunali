package com.emlakjet.finalproject.message.producer;

import com.emlakjet.finalproject.message.dto.AdvertisementDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    @Value("${spring.rabbit.queue.name}")
    private String queueName;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue (AdvertisementDto advertisementDto) {

        System.out.println("Advertisement sent to queue...");
        rabbitTemplate.convertAndSend(queueName,advertisementDto);

    }
}
