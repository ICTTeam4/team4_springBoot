package com.saintkream.server.domain.transaction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;
import com.saintkream.server.domain.transaction.service.TransactionService;
import com.saintkream.server.domain.transaction.vo.TransactionVO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
 private final JdbcTemplate jdbcTemplate;
 public TransactionController(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
}

    @Autowired
    private TransactionService transactionService;

    // POST 요청 : 거래 데이터 저장
    @PostMapping("/settransdetails")
    public DataVO setTransactionDetails(@RequestBody TransactionVO tvo) {
        DataVO dataVO = new DataVO();
        try {

            /* SB insert의 결과 값 받기 */
            int result = transactionService.setTransactionDetails(tvo);
            if (result > 0) {
                dataVO.setSuccess(true);
                dataVO.setMessage("구매 성공");
            }
            return dataVO;
        } catch (Exception e) {
            log.error("오류 발생: {}", e.getMessage());
            dataVO.setMessage("구매 실패: ");
            dataVO.setSuccess(false);
            return dataVO;
        }
    }

    @GetMapping("/gettransdetails")
    public DataVO gettransdetails(@RequestParam("pwr_id") String pwr_id) {
        DataVO dataVO = new DataVO();
        try {
            TransactionVO tvo = transactionService.getTransactionDetails(pwr_id);
            log.info("------------");
            dataVO.setSuccess(true);
            dataVO.setMessage("조회 성공");
            dataVO.setData(tvo);

        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("조회 실패");
        }
        return dataVO;
    }
    @GetMapping("/HayoonSearchPurchase")
    public ResponseEntity<?> HayoonSearchPurchase(@RequestParam("member_id") Long member_id) {
        System.out.println("잘되는지테스트구매내역하윤");
        String sqlTransaction = "SELECT * FROM transactiondetails WHERE buyer_id = ?";
        List<Map<String, Object>> purchases = jdbcTemplate.queryForList(sqlTransaction, member_id);
    
        if (purchases.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions found.");
        }
    
        for (Map<String, Object> purchase : purchases) {
            Long pwrId = (Long) purchase.get("pwr_id");
    
            if (pwrId != null) {
                // post_files 테이블에서 file_id 조회
                String sqlPostFiles = "SELECT file_id FROM post_files WHERE pwr_id = ?";
                List<Map<String, Object>> postFiles = jdbcTemplate.queryForList(sqlPostFiles, pwrId);
    
                if (!postFiles.isEmpty()) {
                    Integer fileId = (Integer) postFiles.get(0).get("file_id");
    
                    // file_table 테이블에서 file_name 조회
                    if (fileId != null) {
                        String sqlFileTable = "SELECT file_name FROM file_table WHERE file_id = ?";
                        List<Map<String, Object>> fileTable = jdbcTemplate.queryForList(sqlFileTable, fileId);
    
                        if (!fileTable.isEmpty()) {
                            String fileName = (String) fileTable.get(0).get("file_name");
                            purchase.put("file_name", fileName); // 결과에 file_name 추가
                            System.out.println("구매내역 fileName: " + fileName);
                            System.out.println("구매내역 fileId: " + fileId);
                        } else {
                            purchase.put("file_name", "0"); // file_name이 없을 경우 "0" 설정
                        }
                    } else {
                        purchase.put("file_name", "0"); // file_id가 없을 경우 "0" 설정
                    }
                } else {
                    purchase.put("file_name", "0"); // post_files에 file_id가 없을 경우 "0" 설정
                }
    
                // post 테이블에서 title 조회
                String sqlPostTitle = "SELECT title FROM post WHERE pwr_id = ?";
                List<Map<String, Object>> postTitles = jdbcTemplate.queryForList(sqlPostTitle, pwrId);
    
                if (!postTitles.isEmpty()) {
                    String title = (String) postTitles.get(0).get("title");
                    purchase.put("title", title); // 결과에 title 추가
                    System.out.println("구매내역 title: " + title);
                } else {
                    purchase.put("title", "제목 없음"); // title이 없을 경우 기본값 설정
                }
            } else {
                purchase.put("file_name", "0"); // pwr_id가 없을 경우 "0" 설정
                purchase.put("title", "제목 없음"); // pwr_id가 없을 경우 기본값 설정
            }
        }
        System.out.println(purchases);
        return ResponseEntity.ok(purchases);
    }
    
    
    @GetMapping("/HayoonSearchSell")
    public ResponseEntity<?> HayoonSearchSell(@RequestParam("member_id") Long member_id) {
        System.out.println("잘되는지테스트판매내역하윤");
        String sqlTransaction = "SELECT * FROM transactiondetails WHERE seller_id = ?";
        List<Map<String, Object>> purchases = jdbcTemplate.queryForList(sqlTransaction, member_id);
    
        if (purchases.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions found.");
        }
    
        for (Map<String, Object> purchase : purchases) {
            Long pwrId = (Long) purchase.get("pwr_id");
    
            if (pwrId != null) {
                // post_files 테이블에서 file_id 조회
                String sqlPostFiles = "SELECT file_id FROM post_files WHERE pwr_id = ?";
                List<Map<String, Object>> postFiles = jdbcTemplate.queryForList(sqlPostFiles, pwrId);
    
                if (!postFiles.isEmpty()) {
                    Integer fileId = (Integer) postFiles.get(0).get("file_id");
    
                    // file_table 테이블에서 file_name 조회
                    if (fileId != null) {
                        String sqlFileTable = "SELECT file_name FROM file_table WHERE file_id = ?";
                        List<Map<String, Object>> fileTable = jdbcTemplate.queryForList(sqlFileTable, fileId);
    
                        if (!fileTable.isEmpty()) {
                            String fileName = (String) fileTable.get(0).get("file_name");
                            purchase.put("file_name", fileName); // 결과에 file_name 추가
                        } else {
                            purchase.put("file_name", "0"); // file_name이 없을 경우 "0" 설정
                        }
                    } else {
                        purchase.put("file_name", "0"); // file_id가 없을 경우 "0" 설정
                    }
                } else {
                    purchase.put("file_name", "0"); // post_files에 file_id가 없을 경우 "0" 설정
                }
    
                // post 테이블에서 title 조회
                String sqlPostTitle = "SELECT title FROM post WHERE pwr_id = ?";
                List<Map<String, Object>> postTitles = jdbcTemplate.queryForList(sqlPostTitle, pwrId);
    
                if (!postTitles.isEmpty()) {
                    String title = (String) postTitles.get(0).get("title");
                    purchase.put("title", title); // 결과에 title 추가
                } else {
                    purchase.put("title", "제목 없음"); // title이 없을 경우 기본값 설정
                }
            } else {
                purchase.put("file_name", "0"); // pwr_id가 없을 경우 "0" 설정
                purchase.put("title", "제목 없음"); // pwr_id가 없을 경우 기본값 설정
            }
        }
        System.out.println(purchases);
        return ResponseEntity.ok(purchases);
    }
    
    
    
}