package com.saintkream.server.domain.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.saintkream.server.domain.auth.vo.UserVO;



@Mapper
public interface AuthMapper {
    UserVO selectMember(@Param("email") String email);
    
}