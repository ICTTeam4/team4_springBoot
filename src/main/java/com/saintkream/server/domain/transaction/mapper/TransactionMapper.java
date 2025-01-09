package com.saintkream.server.domain.transaction.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.transaction.vo.TransactionVO;

@Mapper
public interface TransactionMapper {
    int setTransactionDetails(TransactionVO tvo);
    TransactionVO getTransactionDetails(String pwr_id);
    List<TransactionVO> getBuyData(String buyer_id);
    int updateIsFixed(int pwr_id);
}
