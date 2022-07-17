package com.emlakjet.finalproject.message.service;

import com.emlakjet.finalproject.message.dto.AdvertisementDto;
import com.emlakjet.finalproject.message.entity.Report;
import org.springframework.stereotype.Service;


@Service
public class ReportService {

    public Report createReport(AdvertisementDto advertisementDto){
        try {
            Report report = new Report();
            String title = advertisementDto.getTitle();
            String username = advertisementDto.getUsername();
            Long price = advertisementDto.getPrice();
            report.setMessage(title + " ilanı " + username + " kullanıcısı tarafından " + price + " fiyatı ile oluşturulmuştur.");
            return report;
        } catch (Exception e) {
            return null;
        }
    }
}
