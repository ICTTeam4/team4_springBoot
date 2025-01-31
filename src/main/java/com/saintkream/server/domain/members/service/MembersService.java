package com.saintkream.server.domain.members.service;


import org.springframework.transaction.annotation.Transactional;

import com.saintkream.server.domain.auth.vo.MembersVO;

public interface MembersService {
  public boolean isNameAvailable(String nickname); // 닉네임 중복 확인 - yb

  public boolean isEmailAvailable(String email); // 이메일 중복 확인 - yb

  public int registerMember(MembersVO mvo); // 회원 가입 - yb

  public int registerSNSMember(MembersVO mvo); // 회원 가입 - yb

  // public int getMembersJoin(MembersVO mvo); //수업버전 가입

  public MembersVO getMembersByIdEmail(String email); // 수업버전 중복검사

  @Transactional
  int updateNickname(String email, String newNickname); // 닉네임 업데이트!

  public int updatePassword(MembersVO mvo); // 비밀번호 업데이트( 마이페이지)
  public int updatePasswordByEmail(MembersVO mvo);
  public int updatePhone(MembersVO mvo); // 휴대폰번호 업데이트( 마이페이지)
  public int updateAdvAgree(MembersVO mvo); // 휴대폰번호 업데이트( 마이페이지)
  public int  withdrawMember(MembersVO mvo); // 삭제
  public MembersVO getMemberDetail(String member_id); // 회원 상세 조회회
  public MembersVO getPostMemberDetail(String pwr_id); // 판매 회원 상세 조회회
  




  public void updateProfileImage(String email, String imageUrl); // 이미지 변경 업데이트



  public MembersVO findMemberByPhone(String tel_no);
  public MembersVO findMemberByEmail(String email);

}
