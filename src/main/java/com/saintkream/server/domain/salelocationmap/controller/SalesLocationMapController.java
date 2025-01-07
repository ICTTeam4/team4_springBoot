package com.saintkream.server.domain.salelocationmap.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.salelocationmap.service.SalesLocationMapService;
import com.saintkream.server.domain.salelocationmap.vo.SalesLocationMapVO;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/api/saleslocationmap")
public class SalesLocationMapController {

  @Autowired
  private SalesLocationMapService salesLocationMapService;


  // @GetMapping("/locationinfo")
  // public DataVO getLocationInfo(@RequestParam("id") int pwr_id) {
  //   log.info("pwr_id: {}", pwr_id); // 비어있는 값으로 나옴
  //   DataVO dataVO = new DataVO();
  //   log.info("------1------"); // 여기까지 찜힘
  //   try {
  //     SalesLocationMapVO SLMVO = salesLocationMapService.getSellerLocation(pwr_id);
  //     log.info("SLMVO 값: {}", SLMVO); // 안찍힘
  //     if (SLMVO == null) {
  //       log.error("판매자의 위치 데이터가 없습니다. pwr_id : {}", pwr_id);
  //       dataVO.setSuccess(false);
  //       dataVO.setMessage("판매자 위치 정보를 찾을 수 없습니다.");
  //       return dataVO;
  //   }
  //     dataVO.setSuccess(true);
  //     dataVO.setMessage("조회 성공");
  //     log.info("SLMVO : ", SLMVO);

  //   } catch (Exception e) {
  //     log.info("설마 여기로 떨어지지 않겠지~~~~~~~~~~~~");
  //     dataVO.setSuccess(false);
  //     dataVO.setMessage("조회 실패");
  //   }
  //   return dataVO;
  // }

  @GetMapping("/ddrlocationinfo")
  public DataVO getDdrLocationInfo() {
    
    DataVO dataVO = new DataVO();
    try {
      List<SalesLocationMapVO> list = salesLocationMapService.getDdrLocation();
      
      
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(list);
    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

  @GetMapping("/laundrylocationinfo")
public DataVO getLaundryLocationInfo() {
    DataVO dataVO = new DataVO();
    try {
        // 세탁소 데이터 가져오기
        List<SalesLocationMapVO> list_laundry = salesLocationMapService.getLaundryLocation();

        // TM → WGS84 변환
        for (SalesLocationMapVO laundry : list_laundry) {
            if (laundry.getLaundry_lat() != null && laundry.getLaundry_lng() != null) {
                double[] converted = convertTMToWGS84(laundry.getLaundry_lat(), laundry.getLaundry_lng());
                laundry.setLaundry_lat(converted[0]); // 변환된 위도
                laundry.setLaundry_lng(converted[1]); // 변환된 경도
            }
        }

        dataVO.setSuccess(true);
        dataVO.setMessage("조회 성공");
        dataVO.setData(list_laundry);
        log.info("변환된 list_laundry : {}", list_laundry);
    } catch (Exception e) {
        log.error("세탁소 위치 정보 변환 중 오류 발생", e);
        dataVO.setSuccess(false);
        dataVO.setMessage("조회 실패");
    }
    return dataVO;
}

// TM → WGS84 변환 메서드
private double[] convertTMToWGS84(double x, double y) {
    double RADIANS_PER_DEGREE = Math.PI / 180.0;
    double DEGREES_PER_RADIAN = 180.0 / Math.PI;

    double a = 6378137.0; // Semi-major axis
    double f = 1 / 298.257223563; // Flattening
    double k0 = 1.0; // Scale factor
    double centralMeridian = 127.0; // Central meridian
    double originLatitude = 38.0; // Latitude of origin
    double falseEasting = 200000.0; // False Easting
    double falseNorthing = 500000.0; // False Northing

    // Eccentricity
    double e = Math.sqrt(f * (2 - f));
    double e1sq = e * e / (1 - e * e);

    // Adjust for False Easting/Northing
    x -= falseEasting;
    y -= falseNorthing;

    // Meridian arc length
    double m = y / k0;

    // Calculate latitude
    double mu = m / (a * (1 - Math.pow(e, 2) / 4 - 3 * Math.pow(e, 4) / 64 - 5 * Math.pow(e, 6) / 256));
    double phi1Rad = mu + (3 * e1sq / 2 - 27 * Math.pow(e1sq, 3) / 32) * Math.sin(2 * mu)
            + (21 * e1sq * e1sq / 16 - 55 * Math.pow(e1sq, 4) / 32) * Math.sin(4 * mu)
            + (151 * Math.pow(e1sq, 3) / 96) * Math.sin(6 * mu);

    double n = a / Math.sqrt(1 - Math.pow(e * Math.sin(phi1Rad), 2));
    double t = Math.pow(Math.tan(phi1Rad), 2);
    double c = e1sq * Math.pow(Math.cos(phi1Rad), 2);
    double r = a * (1 - Math.pow(e, 2)) / Math.pow(1 - Math.pow(e * Math.sin(phi1Rad), 2), 1.5);
    double d = x / (n * k0);

    double lat = phi1Rad - (n * Math.tan(phi1Rad) / r) *
            (Math.pow(d, 2) / 2 - (5 + 3 * t + 10 * c - 4 * c * c - 9 * e1sq) * Math.pow(d, 4) / 24
                    + (61 + 90 * t + 298 * c + 45 * t * t - 252 * e1sq - 3 * c * c) * Math.pow(d, 6) / 720);
    lat = lat * DEGREES_PER_RADIAN;

    double lon = (d - (1 + 2 * t + c) * Math.pow(d, 3) / 6
            + (5 - 2 * c + 28 * t - 3 * Math.pow(c, 2) + 8 * e1sq + 24 * t * t) * Math.pow(d, 5) / 120)
            / Math.cos(phi1Rad);
    lon = centralMeridian + lon * DEGREES_PER_RADIAN;

    return new double[]{lat, lon};
}


}
