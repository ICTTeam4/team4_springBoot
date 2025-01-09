package com.saintkream.server.domain.weatherinfo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/weatherinfo")
public class WeatherInfoController {

  // @GetMapping("/forecast")
  // public ResponseEntity<?> getWeatherForecast(
  //     @RequestParam String latitude,
  //     @RequestParam String longitude) {
  //       int nx = (int) Math.floor(Double.parseDouble(latitude)); // 소수점 이하를 버림
  //       int ny = (int) Math.floor(Double.parseDouble(longitude)); // 소수점 이하를 버림
  //       LocalDate now = LocalDate.now();
  //       String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
  //       String baseTime = "0500";
  //       try {
  //         StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
  //         urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=lBKYUQELMdgeDbmYYIh1WPOAHCZl2KkidQ%2F2bAkYNPzMOqlsZ2DAmmpUHitCrrO6xC6vD%2F%2Be%2FKjhog6yp1aWug%3D%3D"); /*Service Key*/
  //         urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
  //         urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
  //         urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
  //         urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
  //         urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시 발표(정시단위) */
  //         urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(60), "UTF-8")); /*예보지점의 X 좌표값*/
  //         urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(127), "UTF-8")); /*예보지점의 Y 좌표값*/
  //         URL url = new URL(urlBuilder.toString());
  //         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
  //         conn.setRequestMethod("GET");
  //         conn.setRequestProperty("Content-type", "application/json");
  //         System.out.println("Response code: " + conn.getResponseCode());
  //         BufferedReader rd;
  //         if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
  //             rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
  //         } else {
  //             rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
  //         }
  //         StringBuilder sb = new StringBuilder();
  //         String line;
  //         while ((line = rd.readLine()) != null) {
  //             sb.append(line);
  //         }
  //         rd.close();
  //         conn.disconnect();
  //         System.out.println(sb.toString());
  //       } catch (Exception e) {
  //         System.out.println(e);
  //       }
     
  //       return null;
  //     }


  @GetMapping("/forecast")
public ResponseEntity<?> getWeatherForecast(
    @RequestParam String latitude,
    @RequestParam String longitude) {
    // 위도와 경도를 기상청 격자 좌표로 변환
    log.info("latitude, longitude : {}, {}", latitude, longitude);
    double lat = Double.parseDouble(latitude);
    double lon = Double.parseDouble(longitude);
    int[] gridCoords = LatLonToGrid.convert(lat, lon);
    int nx = gridCoords[0];
    int ny = gridCoords[1];
    log.info("변경된 nx, ny : {}, {}", nx, ny);

     // base_time 계산
     String baseTime = calculateBaseTime();
     LocalDate now = LocalDate.now();
 
     // 예보 종료일 계산
     int forecastDays = isExtendedForecast(baseTime) ? 4 : 3;
 
    List<Map<String, Object>> weatherData = new ArrayList<>();

    try {
        for (int i = 0; i < forecastDays; i++) { // 5일간 반복
            String baseDate = now.plusDays(i).format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=lBKYUQELMdgeDbmYYIh1WPOAHCZl2KkidQ%2F2bAkYNPzMOqlsZ2DAmmpUHitCrrO6xC6vD%2F%2Be%2FKjhog6yp1aWug%3D%3D");
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(nx), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(ny), "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            System.out.println(sb.toString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(sb.toString());
            JsonNode items = rootNode.path("response").path("body").path("items").path("item");

            List<Map<String, Object>> dailyWeather = new ArrayList<>(); // 타입 통일
        for (JsonNode item : items) {
            String category = item.path("category").asText();
            if (category.equals("SKY")) {
                Map<String, Object> weather = new HashMap<>();
                weather.put("date", baseDate);
                weather.put("time", item.path("baseTime").asText());
                weather.put("value", item.path("fcstValue").asText());
                dailyWeather.add(weather);
            }
        }
        weatherData.addAll(dailyWeather);
    }
        
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving weather forecast");
    }

    return ResponseEntity.ok(weatherData);
}

// 발표 시간 계산
private String calculateBaseTime() {
  LocalTime now = LocalTime.now();
  if (now.isBefore(LocalTime.of(2, 0))) return "2300";
  else if (now.isBefore(LocalTime.of(5, 0))) return "0200";
  else if (now.isBefore(LocalTime.of(8, 0))) return "0500";
  else if (now.isBefore(LocalTime.of(11, 0))) return "0800";
  else if (now.isBefore(LocalTime.of(14, 0))) return "1100";
  else if (now.isBefore(LocalTime.of(17, 0))) return "1400";
  else if (now.isBefore(LocalTime.of(20, 0))) return "1700";
  else if (now.isBefore(LocalTime.of(23, 0))) return "2000";
  else return "2300";
}

// 연장 예보 여부 확인
private boolean isExtendedForecast(String baseTime) {
  return baseTime.equals("0200") || baseTime.equals("0500") || baseTime.equals("0800")
      || baseTime.equals("1100") || baseTime.equals("1400");
}

  
private static class LatLonToGrid {
  public static int[] convert(double lat, double lon) {
      double RE = 6371.00877; // 지구 반경(km)
      double GRID = 5.0; // 격자 간격(km)
      double SLAT1 = 30.0; // 투영 위도1(degree)
      double SLAT2 = 60.0; // 투영 위도2(degree)
      double OLON = 126.0; // 기준점 경도(degree)
      double OLAT = 38.0; // 기준점 위도(degree)
      double XO = 43; // 기준점 X좌표 (격자점)
      double YO = 136; // 기준점 Y좌표 (격자점)

      double DEGRAD = Math.PI / 180.0; // 도(degree)를 라디안(radian)으로 변환

      double re = RE / GRID;
      double slat1 = SLAT1 * DEGRAD;
      double slat2 = SLAT2 * DEGRAD;
      double olon = OLON * DEGRAD;
      double olat = OLAT * DEGRAD;

      double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
      sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
      double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
      sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
      double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
      ro = re * sf / Math.pow(ro, sn);

      double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
      ra = re * sf / Math.pow(ra, sn);
      double theta = lon * DEGRAD - olon;
      if (theta > Math.PI) theta -= 2.0 * Math.PI;
      if (theta < -Math.PI) theta += 2.0 * Math.PI;
      theta *= sn;

      int x = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
      int y = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
      return new int[]{x, y};
  }
}



}

      