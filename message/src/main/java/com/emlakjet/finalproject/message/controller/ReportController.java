package com.emlakjet.finalproject.message.controller;

import com.emlakjet.finalproject.message.dto.AdvertisementDto;
import com.emlakjet.finalproject.message.entity.Report;
import com.emlakjet.finalproject.message.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class ReportController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping
    public ResponseEntity<Report> produceMessage(@RequestBody AdvertisementDto advertisementDto){
        try{
            messageProducer.sendToQueue(advertisementDto);
            return new ResponseEntity("Message Sent.", HttpStatus.ACCEPTED);
        } catch (Exception e){
            return new ResponseEntity("", HttpStatus.BAD_REQUEST);
        }
    }
}
