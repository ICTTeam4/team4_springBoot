package com.saintkream.server.domain.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.auth.vo.MembersVO;
import com.saintkream.server.domain.members.mapper.MembersMapper;



@Service
public class MembersServiceImpl implements MembersService {
    @Autowired
    private MembersMapper membersMapper;

   

   
    @Override
    public boolean isNameAvailable(String nickname) {
        int count = membersMapper.checkNickname(nickname); // Mapper에서 반환된 int 값
        return count == 0; // 중복이 없으면 true 반환
    }

    @Override
    public boolean isEmailAvailable(String email) {
        Integer count = membersMapper.checkEmail(email);
        return count == null || count == 0;
    }
    @Override
     public int registerSNSMember(MembersVO mvo) {
        return membersMapper.insertSNSMember(mvo);
    }
    @Override
     public int registerMember(MembersVO mvo) {
        return membersMapper.insertMember(mvo);
    }

    @Override
    public MembersVO getMembersByIdEmail(String email) {
       return membersMapper.getMembersByIdEmail(email);
    }
}