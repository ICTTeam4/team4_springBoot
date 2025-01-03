package com.saintkream.server.domain.transaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getbuydata")
    public DataVO getBuyData(@RequestParam("member_id") String buyer_id) {
        DataVO dataVO = new DataVO();
        try {
            log.info("구매자아이디 " + buyer_id);
            List<TransactionVO> list = transactionService.getBuyData(buyer_id);
            log.info("리스트정보확인 " + list);
            dataVO.setSuccess(true);
            dataVO.setMessage("조회 성공");
            dataVO.setData(list);
        } catch (Exception e) {
            log.error("Error while fetching buy data for buyer_id: {}", buyer_id, e);
            dataVO.setSuccess(false);
            dataVO.setMessage("조회 실패");
        }
        return dataVO;
    }
}
