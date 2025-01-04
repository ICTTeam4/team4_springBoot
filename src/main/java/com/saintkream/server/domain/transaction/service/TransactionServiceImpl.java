package com.saintkream.server.domain.transaction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saintkream.server.domain.transaction.mapper.TransactionMapper;
import com.saintkream.server.domain.transaction.vo.TransactionVO;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int setTransactionDetails(TransactionVO tvo) {
        return transactionMapper.setTransactionDetails(tvo);
    }

    @Override
    public TransactionVO getTransactionDetails(String pwr_id) {
        return transactionMapper.getTransactionDetails(pwr_id);
    }

    @Transactional
    @Override
    public List<TransactionVO> getBuyData(String buyer_id) {
        return transactionMapper.getBuyData(buyer_id);
    }

 
}
