package com.saintkream.server.domain.admin.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.saintkream.server.common.util.JwtUtil;
import com.saintkream.server.domain.admin.service.AdminService;
import com.saintkream.server.domain.admin.vo.AdmBbsVO;
import com.saintkream.server.domain.admin.vo.AdmCmpVO;
import com.saintkream.server.domain.admin.vo.AdmCtgVO;
import com.saintkream.server.domain.admin.vo.AdmCycleVO;
import com.saintkream.server.domain.admin.vo.AdmMemberVO;
import com.saintkream.server.domain.admin.vo.AdmPopupVO;
import com.saintkream.server.domain.admin.vo.AdmPostVO;
import com.saintkream.server.domain.admin.vo.AdmTransVO;
import com.saintkream.server.domain.admin.vo.AdminVO;
import com.saintkream.server.domain.auth.vo.DataVO;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AdminController {

    @Autowired AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
 

    //카테고리 수정
    @PutMapping("/categorys/{category_id}")
    public void getAdmCtgUpdate(@PathVariable("category_id") String category_id, @RequestBody AdmCtgVO admctgvo) {
        try {
            
            admctgvo.setCategory_id(category_id);

            // Service를 통해 업데이트 처리
            int result = adminService.getAdmCtgUpdate(admctgvo);

        if (result == 0) {
            log.info("Update failed: No rows updated.");
           
        }

        log.info("카테고리 update successful.");
        

        } catch (Exception e) {
            log.error("Exception occurred during 카테고리 update", e);
            
        }  
    
    }            

    // 따릉이 List조회 
    @GetMapping("/cycles")
    @CrossOrigin(origins = "http://localhost:3039") 
    public List<AdmCycleVO> getAdmCycleList() {
        return adminService.getAdmCycleList();
    }    
    


    // 거래관리 List
    @GetMapping("/trans")
    public List<AdmTransVO> getAdmTransList() {
      return adminService.getAdmTransList();
    }        

    // 판매글 List
    @GetMapping("/post")
    public List<AdmPostVO> getAdmPostList() {
      return adminService.getAdmPostList();
    }    

    //판매글
    @GetMapping("/post/{pwr_id}")
    @CrossOrigin(origins = "http://localhost:3039")
    public AdmPostVO getAdmPostListById(@PathVariable("pwr_id") String pwr_id ) {
       return adminService.getAdmPostListById(pwr_id);
    }       

    //판매글 수정
    @PutMapping("/post/{pwr_id}")
    @CrossOrigin(origins = "http://localhost:3039")
    public void getAdmCmpUpdate(@PathVariable("pwr_id") String pwr_id, @RequestBody AdmPostVO admpostvo) {
        try {
            
            admpostvo.setPwr_id(pwr_id);

            // Service를 통해 업데이트 처리
            int result = adminService.getAdmPostUpdate(admpostvo);

        if (result == 0) {
            log.info("Update failed: No rows updated.");
           
        }

        log.info("판매글 update successful.");
        

        } catch (Exception e) {
            log.error("Exception occurred during 판매글 update", e);
            
        }  
    
    }      

    //신고 수정
    @PutMapping("/complaint/{report_id}")
    public void getAdmCmpUpdate(@PathVariable("report_id") String report_id, @RequestBody AdmCmpVO admcmpvo) {
        try {
            
            admcmpvo.setReport_id(report_id);

            // Service를 통해 업데이트 처리
            int result = adminService.getAdmCmpUpdate(admcmpvo);

        if (result == 0) {
            log.info("Update failed: No rows updated.");
           
        }

        log.info("신고 update successful.");
        

        } catch (Exception e) {
            log.error("Exception occurred during 신고 update", e);
            
        }  
    
    }        
    // 신고 List
    @GetMapping("/complaint")
    public List<AdmCmpVO> getAdmCmpList() {
      return adminService.getAdmCmpList();
    }    


    // BBS List
    @GetMapping("/bbs")
    public List<AdmBbsVO> getAdmBbsList(@RequestParam("cs_type") String cs_type) {
        return adminService.getAdmBbsList(cs_type);
    }
    // BBS 생성
    @PostMapping("/bbs/new")
    public void getAdmBbsWrite(@RequestBody AdmBbsVO admbbsvo){
        try {

            int result = adminService.getAdmBbsWrite(admbbsvo);
    
            if (result == 0) {
                log.info("Create failed: No rows created.");
            } else {
                log.info("BBS created successfully.");
            }
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }       

    //BBS 수정
    @PutMapping("/bbs/{cs_id}")
    public void getAdmBbsUpdate(@PathVariable("cs_id") String cs_id, @RequestBody AdmBbsVO admbbsvo) {
        try {
            // admin_id를 AdminVO에 설정
            admbbsvo.setCs_id(cs_id);

            // Service를 통해 업데이트 처리
            int result = adminService.getAdmBbsUpdate(admbbsvo);

        if (result == 0) {
            log.info("Update failed: No rows updated.");
           
        }

        log.info("BBS update successful.");
        

    } catch (Exception e) {
        log.error("Exception occurred during BBS update", e);
        
    }  
    
    }

    @GetMapping("/admins")
    @CrossOrigin(origins = "http://localhost:3039") 
    public List<AdminVO> getAdminList() {
      return adminService.getAdminList();
    }
    
    
    @PutMapping("/admins/{admin_id}")
    public void getAdminUpdate(@PathVariable("admin_id") String admin_id, @RequestBody AdminVO adminvo) {
        try {
            // admin_id를 AdminVO에 설정
            adminvo.setAdmin_id(admin_id);

            // Service를 통해 업데이트 처리
            int result = adminService.getAdminUpdate(adminvo);

        if (result == 0) {
            log.info("Update failed: No rows updated.");
           
        }

        log.info("Admin update successful.");
        

    } catch (Exception e) {
        log.error("Exception occurred during admin update", e);
        
    }  
    
    }

    // Admin 생성 
    @PostMapping("/admins/new")
    public DataVO getAdminWrite(@RequestBody AdminVO adminvo ) {
        DataVO dataVO = new DataVO();
        try {
            // 로그인 여부 확인
            // if (authentication == null) {
            //     dataVO.setSuccess(false);
            //     dataVO.setMessage("로그인이 필요합니다.");
            //     return dataVO;
            // }

            adminvo.setAdmin_password(passwordEncoder.encode(adminvo.getAdmin_password()));
            int result = adminService.getAdminWrite(adminvo);
    
            if (result > 0) {
                dataVO.setSuccess(true);
                dataVO.setMessage("ADMIN 생성 성공");
            }
            return dataVO;
        } catch (Exception e) {
            log.error("ADMIN 생성 오류 발생: {}", e.getMessage());
            dataVO.setMessage("ADMIN 생성 실패: ");
            dataVO.setSuccess(false);
            return dataVO;
        }

    }    

    @PutMapping("/admins/delete")
    public void getAdminDelete(@RequestBody Map<String, String> payload) {
        try {
            String admin_id = payload.get("admin_id"); // JSON 본문에서 admin_id 추출
            if (admin_id == null) {
                log.info("Delete failed: admin_id is missing.");
                return;
            }

            int result = adminService.getAdminDelete(admin_id);

            if (result == 0) {
                log.info("Delete failed: No rows deleted.");
            } else {
                log.info("Admin deleted successfully.");
            }
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3039")
    public DataVO adminLogin(@RequestBody AdminVO avo) {

        log.info(avo.getAdmin_id());
        DataVO dataVO = new DataVO();
        try {
            // 사용자 정보 조회
            AdminVO adminvo = adminService.getAdminById(avo.getAdmin_id());

            if (adminvo == null) {
                dataVO.setSuccess(false);
                dataVO.setMessage("존재하지 않는 ID 입니다.");
                return dataVO;
            }

            // // 비밀번호 검증 받기
            if (!passwordEncoder.matches(avo.getAdmin_password(), adminvo.getAdmin_password())) {
                dataVO.setSuccess(false);
                dataVO.setMessage("비밀번호가 일치하지 않습니다.");
                return dataVO;
            }

            // JWT 토큰 생성 및 전송
            String token = jwtUtil.generateToken(avo.getAdmin_id());

            // SecurityContext에 인증 객체 설정
            // 다른 컨트롤러, 서비스, 또는 보안 필터에서 인증 정보를 쉽게 가져올 수 있습니다.
            // 인증된 사용자 이름이나 권한을 사용해 요청 처리.
            // 인증 객체가 설정되면, Spring Security의 유틸리티 메서드를 사용해 현재 사용자 정보를 쉽게 가져올 수 있습니다.
            // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // String username = auth.getName(); // 인증된 사용자 이름

            // UsernamePasswordAuthenticationToken authenticationToken = new
            // UsernamePasswordAuthenticationToken(
            // membersVO.getM_id(), null, null); // 권한 정보 추가 가능
            // SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            // log.info("로그인 성공, SecurityContext에 인증 객체 설정 완료");

            dataVO.setData(adminvo);
            dataVO.setSuccess(true);
            dataVO.setMessage("로그인 성공");
            dataVO.setToken(token);
            return dataVO;

        } catch (Exception e) {
            dataVO.setSuccess(false);
            dataVO.setMessage("네트워크 오류");
            return dataVO;
        }

    }

    // member list 조회
    @GetMapping("/members")
    @CrossOrigin(origins = "http://localhost:3039")
    public List<AdmMemberVO> getAdmMemberList() {
      return adminService.getAdmMemberList();
    }

    // member 수정
    @PutMapping("/members/{member_id}")
    public void getAdmMemberUpdate(@PathVariable("member_id") String member_id, @RequestBody AdmMemberVO admmbervo) {
        try {
            // admin_id를 AdminVO에 설정
            admmbervo.setMember_id(member_id);

            // Service를 통해 업데이트 처리
            int result = adminService.getAdmMemberUpdate(admmbervo);

        if (result == 0) {
            log.info("Update failed: No rows updated.");
           
        }

        log.info("Member update successful.");
        

    } catch (Exception e) {
        log.error("Exception occurred during Member update", e);
        
    }  
    
    }    
    
    // 팝업 List 가져오기 
    @GetMapping("/popup")
    public List<AdmPopupVO> getPopupList() {
      return adminService.getPopupList();
    }

    // 팝업 생성
    @PostMapping("/popup/new")
    @CrossOrigin(origins = "http://localhost:3039")
    public void getPopupWrite( @ModelAttribute("data") AdmPopupVO admpopupvo) {
        try {
            MultipartFile file = admpopupvo.getFile();
            if (file.isEmpty()) {
                admpopupvo.setF_name("");
            } else {
                UUID uuid = UUID.randomUUID();
                String f_name = uuid.toString() + "_" + file.getOriginalFilename();
                admpopupvo.setF_name(f_name);

                String staticPath = new File("src/main/resources/static").getAbsolutePath();
                File uploadDir = new File(staticPath);


                // 파일 저장
                file.transferTo(new File(uploadDir, f_name));
                log.info("파일 업로드 성공 : ", f_name);
            }

            int result = adminService.getPopupWrite(admpopupvo);
    
            if (result == 0) {
                log.info("Create failed: No rows created.");
                
            } else {
                log.info("banner created successfully.");
            }
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }   
    // 팝업 수정 
  @PutMapping("/popup/{pu_id}")
  public ResponseEntity<String> getPopupUpdate(
          @PathVariable("pu_id") String pu_id,
          @ModelAttribute("data") AdmPopupVO admpopupvo) {

      try {
          MultipartFile file = admpopupvo.getFile();
          
          // Check if file is present and not empty
          if (file != null && !file.isEmpty()) {
              UUID uuid = UUID.randomUUID();
              String f_name = uuid.toString() + "_" + file.getOriginalFilename();
              admpopupvo.setF_name(f_name);
              
              // Get the file path from application properties or use default path
              String staticPath = new File("src/main/resources/static").getAbsolutePath(); 
              File uploadDir = new File(staticPath);

              // Save the file to the directory
              file.transferTo(new File(uploadDir, f_name));
              log.info("파일 업로드 성공: " + f_name);
          } else {
              // If no file, set f_name to empty string (or you can handle differently)
              admpopupvo.setF_name("");
          }

          // Set the popup ID
          admpopupvo.setPu_id(pu_id);

          // Update using service layer
          int result = adminService.getPopupUpdate(admpopupvo);
          
          // Check if update was successful
          if (result == 0) {
              log.error("Update failed: No rows updated for pu_id: " + pu_id);
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update failed: No rows updated");
          }
          
          log.info("팝업 update successful for pu_id: " + pu_id);
          return ResponseEntity.ok("Popup updated successfully");

        } catch (Exception e) {
          log.error("Exception occurred during 팝업 update", e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
      }
  }    

      // 대분류 category 조회
      @GetMapping("/categorys")
      @CrossOrigin(origins = "http://localhost:3039") 
      public List<AdmCtgVO> getAdmCtgList() {
          return adminService.getAdmCtgList();
      }
  
      // 중분류 category 조회
      @GetMapping("/categorys2")
      @CrossOrigin(origins = "http://localhost:3039") 
      public List<AdmCtgVO> getAdmCtgList2(@RequestParam("ctg_level") String ctg_level) {
          return adminService.getAdmCtgList2(ctg_level);
      }    

      // category 생성
      @PostMapping("/categorys/new")
      public void getAdmCtgWrite(@RequestBody AdmCtgVO admctgvo){
          try {
  
              int result = adminService.getAdmCtgWrite(admctgvo);
      
              if (result == 0) {
                  log.info("Create failed: No Category created.");
              } else {
                  log.info("Category created successfully.");
              }
          } catch (Exception e) {
              log.error("Exception: ", e);
          }
      }      
  
}
