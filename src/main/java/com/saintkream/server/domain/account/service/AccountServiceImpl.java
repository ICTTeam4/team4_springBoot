package com.saintkream.server.domain.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.account.mapper.AccountMapper;
import com.saintkream.server.domain.auth.vo.AccountVO;

@Service
public class AccountServiceImpl implements AccountService { // interface → class로 수정

    @Autowired
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public void addAccount(AccountVO accountVO) {
        accountMapper.insertAccount(accountVO);
    }

    @Override
    public void updateAccount(Long id, AccountVO accountVO) {
        accountVO.setId(id); 
        accountMapper.updateAccount(accountVO);
    }

    @Override
    public void deleteAccount(Long id) {
        accountMapper.deleteAccount(id);
    }

    @Override
    public void setDefaultAccount(Long id, Long member_id) {
        accountMapper.clearDefaultAccount(member_id); 
        accountMapper.setDefaultAccount(id);
    }

    @Override
    public List<AccountVO> getAccountsByMemberId(Long member_id) {
        return accountMapper.getAccountsByMemberId(member_id);
    }

    @Override
    public void clearDefaultAccount(Long member_id) {
        accountMapper.clearDefaultAccount(member_id);
    }
}
