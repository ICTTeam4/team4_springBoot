package com.saintkream.server.domain.transaction.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.transaction.vo.TransactionVO;

@Mapper
public interface TransactionMapper {
    int setTransactionDetails(TransactionVO tvo);
    TransactionVO getTransactionDetails(String pwr_id);
}
