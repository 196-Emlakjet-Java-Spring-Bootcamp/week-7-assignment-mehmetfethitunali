package com.emlakjet.finalproject.message.dao;

import com.emlakjet.finalproject.message.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
