package com.saintkream.server.domain.transaction.service;

import com.saintkream.server.domain.transaction.vo.TransactionVO;

public interface TransactionService {
    int setTransactionDetails(TransactionVO tvo);
    TransactionVO getTransactionDetails(String pwr_id);
}
