package com.saintkream.server.domain.members.service;

import com.saintkream.server.domain.auth.vo.MembersVO;

public interface MembersService {
  boolean isNameAvailable(String nickname); // 닉네임 중복 확인 - yb

  boolean isEmailAvailable(String email); // 이메일 중복 확인 - yb 

  public int registerMember(MembersVO mvo); // 회원 가입 -  yb 

  // public int getMembersJoin(MembersVO mvo); //수업버전 가입 

  public MembersVO getMembersById(String m_id); // 수업버전 중복검사 

}
