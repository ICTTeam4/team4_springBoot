package com.saintkream.server.domain.members.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.auth.vo.MembersVO;

@Mapper
public interface MembersMapper {
    // public int getMembersJoin(MembersVO mvo);

    public MembersVO getMembersByIdEmail(String email );

    public MembersVO findUserByProvider(MembersVO mvo);

    public int checkNickname(@Param("nickname") String nickname); // 닉네임 중복 확인 yb

    public Integer checkEmail(@Param("email") String email); // 이메일 중복 확인 yb

    public int insertSNSMember(MembersVO mvo); // 회원 가입 HY
    public int insertMember(MembersVO mvo); // 회원 가입 yb
    public  int updateNickname(@Param("email") String email, @Param("newNickname") String newNickname);
    public int updatePassword(MembersVO mvo);
    public int updatePhone(MembersVO mvo);
    public int updateAdvAgree(MembersVO mvo);
    public int  withdrawMember(MembersVO mvo);
}