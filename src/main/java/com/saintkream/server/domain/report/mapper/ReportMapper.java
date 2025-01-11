package com.saintkream.server.domain.report.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.report.vo.ReportVO;

@Mapper
public interface ReportMapper {
     int insertReport(ReportVO rvo);
}
