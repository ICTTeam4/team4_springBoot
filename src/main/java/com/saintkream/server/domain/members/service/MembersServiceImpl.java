package com.saintkream.server.domain.members.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.auth.vo.MembersVO;
import com.saintkream.server.domain.members.mapper.MembersMapper;

import lombok.extern.slf4j.Slf4j;


@Slf4j
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

    @Override
    public int updateNickname(String email, String newNickname) {
        return membersMapper.updateNickname(email, newNickname);
    }

    @Override
    public int updatePassword(MembersVO mvo) {
        return membersMapper.updatePassword(mvo);
    }

    @Override
    public int updatePhone(MembersVO mvo) {
        return membersMapper.updatePhone(mvo);
    }

    @Override
    public int updateAdvAgree(MembersVO mvo) {
        return membersMapper.updateAdvAgree(mvo);
    }

    @Override
    public int  withdrawMember(MembersVO mvo) {
        return membersMapper.withdrawMember(mvo);
    }

    @Override
    public MembersVO getMemberDetail(String member_id) {
        return membersMapper.getMemberDetail(member_id);        
    }

    @Override
    public void updateProfileImage(String email, String imageUrl) {
        membersMapper.updateProfileImage(email, imageUrl);
    }

    @Override
    public MembersVO getPostMemberDetail(String pwr_id) {
        return membersMapper.getPostMemberDetail(pwr_id);
    }

    @Override
    public MembersVO findMemberByPhone(String tel_no) {
        log.info("findMemberByPhone 호출됨 - 입력된 전화번호: {}", tel_no);
        MembersVO member = membersMapper.findMemberByPhone(tel_no);
        log.info("findMemberByPhone 결과 - 멤버 정보: {}", member);
        return member;
    }

    @Override
    public MembersVO findMemberByEmail(String email) {
        return membersMapper.findMemberByEmail(email);
    }

    @Override
    public int updatePasswordByEmail(MembersVO mvo) {
        return membersMapper.updatePasswordByEmail(mvo);
    }
}