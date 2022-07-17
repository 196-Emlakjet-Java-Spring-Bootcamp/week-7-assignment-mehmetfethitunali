package com.emlakjet.finalproject.message.listener;

import com.emlakjet.finalproject.message.dao.ReportRepository;
import com.emlakjet.finalproject.message.dto.AdvertisementDto;
import com.emlakjet.finalproject.message.entity.Report;
import com.emlakjet.finalproject.message.service.ReportService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    ReportService reportService;

    @Autowired
    ReportRepository reportRepository;

    @RabbitListener(queues = "message-queue")
    public void handleOperation (AdvertisementDto advertisementDto) throws InterruptedException {

        System.out.println("Advertisement Message recieved...");

        Report report = reportService.createReport(advertisementDto);

        reportRepository.save(report);

    }
}
