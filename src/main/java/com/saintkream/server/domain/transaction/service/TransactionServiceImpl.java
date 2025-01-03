package com.saintkream.server.domain.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.transaction.mapper.TransactionMapper;
import com.saintkream.server.domain.transaction.vo.TransactionVO;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public int setTransactionDetails(TransactionVO tvo) {
        return transactionMapper.setTransactionDetails(tvo);
    }

    @Override
    public TransactionVO getTransactionDetails(String pwr_id) {
        return transactionMapper.getTransactionDetails(pwr_id);
    }

 
}
