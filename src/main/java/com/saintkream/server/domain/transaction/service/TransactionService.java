package com.saintkream.server.domain.transaction.service;

import java.util.List;

import com.saintkream.server.domain.transaction.vo.TransactionVO;

public interface TransactionService {
    int setTransactionDetails(TransactionVO tvo);
    TransactionVO getTransactionDetails(String pwr_id);
    List<TransactionVO> getBuyData(String buyer_id);
}
