package com.saintkream.server.domain.address.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saintkream.server.domain.address.service.AddressService;
import com.saintkream.server.domain.auth.vo.AddressVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/address")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addAddress(@RequestBody AddressVO addressVO) {
        log.info("주소 추가 요청: {}", addressVO);
        addressService.addAddress(addressVO);
        log.info("주소 추가 완료");

        Map<String, String> response = new HashMap<>();
        response.put("message", "주소가 성공적으로 추가되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{add_id}")
    public ResponseEntity<Map<String, String>> updateAddress(
            @PathVariable("add_id") String add_id,
            @RequestBody AddressVO addressVO) {
        if (add_id == null || add_id.isEmpty() || "undefined".equals(add_id)) {
            throw new IllegalArgumentException("add_id 값이 비어있거나 유효하지 않습니다.");
        }
    
        log.info("주소 수정 요청 - add_id: {}, 데이터: {}", add_id, addressVO);
        addressService.updateAddress(add_id, addressVO);
        log.info("주소 수정 완료 - ID: {}", add_id);
    
        Map<String, String> response = new HashMap<>();
        response.put("message", "주소가 성공적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }
    
    

    @DeleteMapping("/delete/{add_id}")
    public ResponseEntity<Map<String, String>> deleteAddress(@PathVariable("add_id") String add_id) {
        log.info("주소 삭제 요청 - ID: {}", add_id);
        addressService.deleteAddress(add_id);
        log.info("주소 삭제 완료 - ID: {}", add_id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "주소가 성공적으로 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/set-default/{add_id}")
    public ResponseEntity<Map<String, String>> setDefaultAddress(
        @PathVariable("add_id") String add_id,
        @RequestParam("member_id") String member_id) {
        if (add_id == null || member_id == null) {
            throw new IllegalArgumentException("add_id 또는 member_id가 비어 있습니다.");
        }
    
        addressService.setDefaultAddress(add_id, member_id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "기본 배송지가 설정되었습니다.");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/list")
    public ResponseEntity<?> getAddressList(@RequestParam("member_id") String member_id) {
        log.info("주소 목록 조회 요청 - Member ID: {}", member_id);
        var addresses = addressService.getAddressList(member_id);
        log.info("주소 목록 조회 완료 - Member ID: {}, 주소 수: {}", member_id, addresses.size());
        return ResponseEntity.ok(addresses);
    }
}
