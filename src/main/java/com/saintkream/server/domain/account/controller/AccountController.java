package com.saintkream.server.domain.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.saintkream.server.domain.account.service.AccountService;
import com.saintkream.server.domain.auth.vo.AccountVO;

import lombok.extern.slf4j.Slf4j;

@RestController

@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Map<String, String>> addAccount(@RequestBody AccountVO account) {
        log.info("계좌 추가 됐니? : {}", account);
        Map<String, String> response = new HashMap<>();
        try {
            accountService.addAccount(account);
            response.put("message", "계좌 추가 완료!");
            log.info("POST /accounts - 응답 데이터: {}", response);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("error", "계좌 추가 중 오류");
            response.put("details", e.getMessage());
            log.error("POST /accounts - 오류: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAccount(@PathVariable Long id, @RequestBody AccountVO account) {
        log.info("계좌 업데이트 됐니? : {}", id, account);
        Map<String, String> response = new HashMap<>();
        try {
            account.setId(id); // AccountVO에 id 설정
            accountService.updateAccount(id, account); // id와 account를 전달
            response.put("message", "계좌 업데이트 완료!");
            log.info("PUT /accounts/{} - 응답 데이터: {}", id, response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "계좌 업데이트 중 오류");
            response.put("details", e.getMessage());
            log.error("PUT /accounts/{} - 오류: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id) {
        log.info("계좌 삭제 됐니?: {}", id, id);
        Map<String, String> response = new HashMap<>();
        try {
            accountService.deleteAccount(id);
            response.put("message", "계좌 삭제 완료!");
            log.info("DELETE /accounts/{} - 응답 데이터: {}", id, response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "계좌 삭제 중 오류");
            response.put("details", e.getMessage());
            log.error("DELETE /accounts/{} - 오류: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}/set-default")
    public ResponseEntity<Map<String, String>> setDefaultAccount(@PathVariable Long id, @RequestParam Long memberId) {
        log.info(" 기본 정산계좌로 바뀌었니?: memberId={}, accountId={}", id, memberId);
        Map<String, String> response = new HashMap<>();
        try {
            accountService.setDefaultAccount(id, memberId);
            response.put("message", "기본 계좌가 설정되었습니다.");
            log.info("PUT /accounts/{}/set-default - 응답 데이터: {}", id, response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "기본 계좌 설정 중 오류");
            response.put("details", e.getMessage());
            log.error("PUT /accounts/{}/set-default - 오류: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAccountsByMemberId(@RequestParam Long memberId) {
        log.info(" 계좌 목록 조회 요청 데이터: memberId={}", memberId);
        try {
            List<AccountVO> accounts = accountService.getAccountsByMemberId(memberId);
            log.info("GET /accounts - 응답 데이터: {}", accounts);
            return ResponseEntity.ok(accounts); // JSON 형식으로 반환
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "계좌 목록 조회 중 오류");
            response.put("details", e.getMessage());
            log.error("GET /accounts - 오류: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
