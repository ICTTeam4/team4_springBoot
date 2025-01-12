package com.saintkream.server.domain.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.admin.mapper.AdminMapper;
import com.saintkream.server.domain.admin.vo.AdmBbsVO;
import com.saintkream.server.domain.admin.vo.AdmCmpVO;
import com.saintkream.server.domain.admin.vo.AdmCtgVO;
import com.saintkream.server.domain.admin.vo.AdmCycleVO;
import com.saintkream.server.domain.admin.vo.AdmMemberVO;
import com.saintkream.server.domain.admin.vo.AdmPopupVO;
import com.saintkream.server.domain.admin.vo.AdmPostVO;
import com.saintkream.server.domain.admin.vo.AdmTransVO;
import com.saintkream.server.domain.admin.vo.AdminVO;


@Service
public class AdminServiceImpl implements AdminService{

  @Autowired AdminMapper adminMapper;

  @Override
  public List<AdminVO> getAdminList() {
    
    return adminMapper.getAdminList();
  }

  @Override
  public AdminVO getAdminById(String admin_id) {
    
    return adminMapper.getAdminById(admin_id);
  }

  @Override
  public int getAdminUpdate(AdminVO admvo) {
    
    return adminMapper.getAdminUpdate(admvo);
  }

  @Override
  public int getAdminDelete(String admin_id) {
    
    return adminMapper.getAdminDelete(admin_id);
  }

  @Override
  public int getAdminWrite(AdminVO admvo) {
   
    return adminMapper.getAdminWrite(admvo);
    
  }

  @Override
  public List<AdmMemberVO> getAdmMemberList() {
    
    return adminMapper.getAdmMemberList();
  }

  @Override
  public int getAdmMemberUpdate(AdmMemberVO admmbervo) {
   
    return adminMapper.getAdmMemberUpdate(admmbervo);
  }

  @Override
  public int getAdmMemberDelete(String member_id) {
    
    return adminMapper.getAdmMemberDelete(member_id);
  }

  @Override
  public List<AdmCtgVO> getAdmCtgList2(String ctg_level) {
    return adminMapper.getAdmCtgList2(ctg_level);
  }

  @Override
  public int getAdmCtgWrite(AdmCtgVO admctgvo) {
    return adminMapper.getAdmCtgWrite(admctgvo);
  }

  @Override
  public int getAdmCtgUpdate(AdmCtgVO admctgvo) {
    return adminMapper.getAdmCtgUpdate(admctgvo);
  }

  @Override
  public int getAdmCtgDelete(String category_id) {
    return adminMapper.getAdmCtgDelete(category_id);
  }

  // @Override
  // public List<AdmCtgVO> getAdmCtgByPid(String pctg_id) {
  //   return adminMapper.getAdmCtgByPid(pctg_id);
  // }

  @Override
  public List<AdmCtgVO> getAdmCtgList() {
    
    return adminMapper.getAdmCtgList();
  }

  @Override
  public List<AdmPopupVO> getPopupList() {
   
    return adminMapper.getPopupList();
  }

  @Override
  public AdmPopupVO getPopupById(String pu_id) {
    
    return adminMapper.getPopupById(pu_id);
  }

  @Override
  public int getPopupUpdate(AdmPopupVO admpopupvo) {
    
    return adminMapper.getPopupUpdate(admpopupvo);
  }

  @Override
  public int getPopupDelete(String pu_id) {
    
    return adminMapper.getPopupDelete(pu_id);
  }

  @Override
  public int getPopupWrite(AdmPopupVO admpopupvo) {
    
    return adminMapper.getPopupWrite(admpopupvo);
  }

  @Override
  public List<AdmBbsVO> getAdmBbsList(String cs_type) {
    return adminMapper.getAdmBbsList(cs_type);
  }

  @Override
  public AdmBbsVO getAdmBbsById(String pu_id) {
    return adminMapper.getAdmBbsById(pu_id);
  }

  @Override
  public int getAdmBbsUpdate(AdmBbsVO admbbsvo) {
    return adminMapper.getAdmBbsUpdate(admbbsvo);
  }

  @Override
  public int getAdmBbsDelete(String cs_id) {
    return adminMapper.getAdmBbsDelete(cs_id);
  }

  @Override
  public int getAdmBbsWrite(AdmBbsVO admbbsvo) {
    return adminMapper.getAdmBbsWrite(admbbsvo);
  }
  // 신고 리스트
  @Override
  public List<AdmCmpVO> getAdmCmpList() {
    return adminMapper.getAdmCmpList();
  }

  // 신고 수정 
  @Override
  public int getAdmCmpUpdate(AdmCmpVO admcmpvo) {
    return adminMapper.getAdmCmpUpdate(admcmpvo);
  }

  // 신고 생성
  @Override
  public int getAdmCmpWrite(AdmCmpVO admcmpvo) {
    return adminMapper.getAdmCmpWrite(admcmpvo);
  }

  @Override
  public List<AdmPostVO> getAdmPostList() {
    return adminMapper.getAdmPostList();
  }

  @Override
  public int getAdmPostUpdate(AdmPostVO admpostvo) {
    return adminMapper.getAdmPostUpdate(admpostvo);
  }

  @Override
  public List<AdmTransVO> getAdmTransList() {
    return adminMapper.getAdmTransList();
  }

  @Override
  public List<AdmCycleVO> getAdmCycleList() {
     return adminMapper.getAdmCycleList();
  }

  @Override
  public AdmPostVO getAdmPostListById(String pwr_id ) {
    return adminMapper.getAdmPostListById(pwr_id);
  }

  @Override
  public int getAdmTransUpdate(AdmTransVO admtransvo) {
    return adminMapper.getAdmTransUpdate(admtransvo);
  }



}
