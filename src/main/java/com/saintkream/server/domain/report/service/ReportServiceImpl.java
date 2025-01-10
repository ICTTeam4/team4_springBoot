package com.saintkream.server.domain.report.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.report.mapper.ReportMapper;
import com.saintkream.server.domain.report.vo.ReportVO;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public int insertReport(ReportVO rvo) {
      return reportMapper.insertReport(rvo);
    }

}
