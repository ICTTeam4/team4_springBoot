package com.saintkream.server.domain.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.report.service.ReportService;
import com.saintkream.server.domain.report.vo.ReportVO;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/report")
public class ReportController {


    @Autowired
    private ReportService reportService;


     @PostMapping
    public ResponseEntity<String> createReport(@RequestBody ReportVO report) {
        log.info("신고 된 거 맞냐???? " + report );
        try {
            reportService.insertReport(report);
            return ResponseEntity.ok("신고가 성공적으로 접수되었습니다!");
        } catch (Exception e) {
            log.info("신고 오류 떴따!!!!!!!!! " + report );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("신고 처리 중 오류가 발생했습니다.");
        }
    }

}
