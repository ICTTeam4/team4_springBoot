package com.saintkream.server.domain.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.admin.vo.AdmBbsVO;
import com.saintkream.server.domain.admin.vo.AdmCmpVO;
import com.saintkream.server.domain.admin.vo.AdmCtgVO;
import com.saintkream.server.domain.admin.vo.AdmCycleVO;
import com.saintkream.server.domain.admin.vo.AdmMemberVO;
import com.saintkream.server.domain.admin.vo.AdmPopupVO;
import com.saintkream.server.domain.admin.vo.AdmPostVO;
import com.saintkream.server.domain.admin.vo.AdmTransVO;
import com.saintkream.server.domain.admin.vo.AdminVO;


@Mapper
public interface AdminMapper {

  // Login 처리
  AdminVO getAdminById(String admin_id);
    
  // Admin 조회 
  List<AdminVO> getAdminList();
    
  // Admin 수정
  int getAdminUpdate(AdminVO admvo);

  // Admin 삭제
  int getAdminDelete(String admin_id);

  // Admin 생성
  int getAdminWrite(AdminVO admvo);

  // Member 조회 
  List<AdmMemberVO> getAdmMemberList();
    
  // Member 수정
  int getAdmMemberUpdate(AdmMemberVO admmbervo);

  // Member 삭제
  int getAdmMemberDelete(String member_id);

  // Category 대분류 조회 
  List<AdmCtgVO> getAdmCtgList();

  // Category 중분류 조회 
  List<AdmCtgVO> getAdmCtgList2(String ctg_level);
	
	// Category 생성
  int getAdmCtgWrite(AdmCtgVO admctgvo);
    
  // Category 수정
  int getAdmCtgUpdate(AdmCtgVO admctgvo);

  // Category 삭제
  int getAdmCtgDelete(String category_id);  

  // 팝업 리스트   
  List<AdmPopupVO> getPopupList();
	
  // 팝업 detail
  AdmPopupVO getPopupById(String pu_id);

  // 팝업 수정 
  int getPopupUpdate(AdmPopupVO admpopupvo);

  // 팝업 삭제
  int getPopupDelete(String pu_id);

  // 팝업 생성
  int getPopupWrite(AdmPopupVO admpopupvo);

  // 게시물(공지사항,FAQ) 리스트   
  List<AdmBbsVO> getAdmBbsList(String cs_type);
	
  // 게시물(공지사항,FAQ) detail
  AdmBbsVO getAdmBbsById(String pu_id);

  // 게시물(공지사항,FAQ) 수정 
  int getAdmBbsUpdate(AdmBbsVO admbbsvo);

  // 게시물(공지사항,FAQ) 삭제
  int getAdmBbsDelete(String cs_id);

  // 게시물(공지사항,FAQ) 생성
  int getAdmBbsWrite(AdmBbsVO admbbsvo);

  // 신고 리스트   
  List<AdmCmpVO> getAdmCmpList();
	
  // 신고 수정 
  int getAdmCmpUpdate(AdmCmpVO admcmpvo);
 
  // 신고 생성
  int getAdmCmpWrite(AdmCmpVO admcmpvo);  

      // 판매글 리스트   
  List<AdmPostVO> getAdmPostList();

  // 판매글 상세 조회 
  AdmPostVO getAdmPostListById(String pwr_id);
	
  // 판매글 수정 
  int getAdmPostUpdate(AdmPostVO admpostvo);

  // 거래글 리스트   
  List<AdmTransVO> getAdmTransList();  
  
  // 거래글 수정 
  int getAdmTransUpdate(AdmTransVO admtransvo);  

  // 따릉이 리스트 조회 
  List<AdmCycleVO> getAdmCycleList();
}
