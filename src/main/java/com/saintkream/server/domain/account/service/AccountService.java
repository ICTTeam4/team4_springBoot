package com.saintkream.server.domain.account.service;

import java.util.List;

import com.saintkream.server.domain.auth.vo.AccountVO;

public interface AccountService {
    void addAccount(AccountVO accountVO);
    void updateAccount(Long id, AccountVO accountVO);
    void deleteAccount(Long id);
    void setDefaultAccount(Long id, Long member_id);
    void clearDefaultAccount(Long member_id);
    List<AccountVO> getAccountsByMemberId(Long member_id);
}
