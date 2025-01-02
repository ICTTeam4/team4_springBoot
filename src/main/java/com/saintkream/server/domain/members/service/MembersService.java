package com.saintkream.server.domain.members.service;

import com.saintkream.server.domain.auth.vo.MembersVO;

public interface MembersService {
  public boolean isNameAvailable(String nickname); // 닉네임 중복 확인 - yb

  public boolean isEmailAvailable(String email); // 이메일 중복 확인 - yb 

  public int registerMember(MembersVO mvo); // 회원 가입 -  yb 
  public int registerSNSMember(MembersVO mvo); // 회원 가입 -  yb 

  // public int getMembersJoin(MembersVO mvo); //수업버전 가입 

  public MembersVO getMembersByIdEmail(String email); // 수업버전 중복검사 


  int updateNickname(String email, String newNickname); // 닉네임 업데이트!

  public void updateProfileImage(String email, String imageUrl); // 이미지 변경 업데이트

}
