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
        return membersMapper.checkNickname(nickname) == 0;
    }

    @Override
    public boolean isEmailAvailable(String email) {
        Integer count = membersMapper.checkEmail(email);
        return count == null || count == 0;
    }
    @Override
     public int registerMember(MembersVO mvo) {
        return membersMapper.insertMember(mvo);
    }

    @Override
    public MembersVO getMembersById(String m_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMembersById'");
    }
}