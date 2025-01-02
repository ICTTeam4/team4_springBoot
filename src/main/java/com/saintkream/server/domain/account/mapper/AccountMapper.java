package com.saintkream.server.domain.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.auth.vo.AccountVO;


@Mapper
public interface AccountMapper {
         // 계좌 추가
    void insertAccount(AccountVO accountVO);

    // 계좌 수정
    void updateAccount(AccountVO accountVO);

    // 계좌 삭제
    void deleteAccount(Long id);

    // 기본 계좌 처리
    void clearDefaultAccount(Long userId);  // 사용자의 모든 계좌에서 기본 설정 제거
    void setDefaultAccount(Long id);       // 특정 계좌를 기본 계좌로 설정

    // 계좌 목록 조회
    List<AccountVO> getAccountsByMemberId(Long userId);
}
