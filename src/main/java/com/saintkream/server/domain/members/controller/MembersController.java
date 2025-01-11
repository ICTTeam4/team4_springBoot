package com.saintkream.server.domain.members.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saintkream.server.common.util.JwtUtil;
import com.saintkream.server.domain.auth.vo.DataVO;
import com.saintkream.server.domain.auth.vo.MembersVO;
import com.saintkream.server.domain.auth.vo.PasswordCheckRequest;
import com.saintkream.server.domain.members.service.MembersService;
import com.saintkream.server.domain.salespost.vo.SalesPostVO;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.http.MediaType;

@Slf4j
@RestController
@RequestMapping("/members")
public class MembersController {
    @Autowired
    private MembersService membersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private final DefaultMessageService messageService;

    // private final PasswordEncoder passwordEncoder;
    // public MembersController(PasswordEncoder passwordEncoder) {
    // this.passwordEncoder = passwordEncoder;
    // }

    public MembersController() {
        // Nurigo SDK 초기화 - 반드시 유효한 API 키와 Secret Key를 사용하세요.
        this.messageService = NurigoApp.INSTANCE.initialize("NCSGKVNIYHHRODVD", "CZ3BPK0QLYSQJ7VCPP9MUKWJ3VUBOXEE",
                "https://api.coolsms.co.kr");
    }

    // 휴대폰 인증 요청
    @PostMapping("/send-phone-auth")
    @ResponseBody
    public ResponseEntity<?> sendPhoneAuth(@RequestParam("phone") String phoneNumber) {
        try {
            System.out.println("인증증");
            Message message = new Message();
            message.setFrom("010-2826-0931"); // 발신 번호 입력
            message.setTo(phoneNumber);
            message.setText("인증번호는 240725."); // 여기서 인증번호를 동적으로 생성하여 전송 가능

            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            System.out.println("SMS 발송 성공: " + response);

            return ResponseEntity.ok(Map.of("message", "인증번호가 전송되었습니다.", "status", "success"));
        } catch (Exception e) {
            System.err.println("SMS 발송 실패: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", "SMS 발송에 실패했습니다.", "status", "failure"));
        }
    }

    // 인증번호 검증 (추가 구현 필요)
    @PostMapping("/verify-phone-auth")
    @ResponseBody
    public ResponseEntity<?> verifyPhoneAuth(@RequestParam("phone") String phoneNumber,
            @RequestParam("code") String code) {
        // 인증번호 검증 로직 구현 필요
        if ("240725".equals(code)) { // 예제에서 인증번호를 123456으로 설정
            return ResponseEntity.ok(Map.of("message", "인증 성공", "status", "success"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "인증번호가 올바르지 않습니다.", "status", "failure"));
        }
    }

    @ResponseBody
    @GetMapping("/check-nickname")
    public boolean checkNickname(@RequestParam("nickname") String nickname) {
        System.out.println("이름 확인!!!!!!!: " + nickname); // 요청된 name 값 출력
        return membersService.isNameAvailable(nickname);
    }

    @ResponseBody
    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam("email") String email) {
        System.out.println("이메일 확인!!!!!!!: " + email); // 요청된 name 값 출력
        return membersService.isEmailAvailable(email);
    }

    @PostMapping("/register")
    @ResponseBody
    public DataVO registerMember(@RequestBody MembersVO mvo) {
        DataVO dataVO = new DataVO();
        try {
            // 닉네임 중복 체크
            if (!membersService.isNameAvailable(mvo.getNickname())) {
                dataVO.setSuccess(false);
                dataVO.setMessage("닉네임이 이미 사용 중입니다.");
                return dataVO;
            }

            // 이메일 중복 체크
            if (!membersService.isEmailAvailable(mvo.getEmail())) {
                dataVO.setSuccess(false);
                dataVO.setMessage("이메일이 이미 사용 중입니다.");
                return dataVO;
            }

            // 비밀번호 확인
            if (!mvo.getPassword().equals(mvo.getConfirmPassword())) {
                dataVO.setSuccess(false);
                dataVO.setMessage("비밀번호가 일치하지 않습니다.");
                return dataVO;
            }

            // 비밀번호 암호화
            mvo.setPassword(passwordEncoder.encode(mvo.getPassword()));

            // 회원가입 처리
            int result = membersService.registerMember(mvo);
            if (result > 0) {
                dataVO.setSuccess(true);
                dataVO.setMessage("회원가입 성공");
                // 추가적으로 토큰 생성이나 필요한 데이터를 여기에 추가 가능
                dataVO.setToken("생성된 토큰 값"); // 나중에 실제 토큰 생성 로직으로 대체
                dataVO.setData(mvo); // 가입된 회원 정보 전달 (필요시)
            } else {
                dataVO.setSuccess(false);
                dataVO.setMessage("회원가입 처리 중 오류가 발생했습니다.");
            }
            return dataVO;
        } catch (Exception e) {
            log.error("회원가입 중 오류 발생: {}", e.getMessage());
            dataVO.setSuccess(false);
            dataVO.setMessage("회원가입 실패: " + e.getMessage());
            return dataVO;
        }
    }

    @PostMapping("/login")
    public DataVO membersLogin(@RequestBody MembersVO mvo) {
        log.info("로그인 요청 데이터!!!!!!!: {}", mvo);
        DataVO dataVO = new DataVO();
        try {
            MembersVO membersVO = membersService.getMembersByIdEmail(mvo.getEmail());

            if (membersVO == null) {
                dataVO.setSuccess(false);
                dataVO.setMessage("존재하지 않는 이메일입니다.");
                return dataVO;
            }

             // 1) status 확인
        //    만약 'INACTIVE' 상태면 로그인 실패 처리
        if ("INACTIVE".equalsIgnoreCase(membersVO.getStatus())) {
            dataVO.setSuccess(false);
            dataVO.setMessage("해당 계정은 탈퇴 또는 비활성 상태입니다.");
            return dataVO;
        }


            // 비밀번호 검증
            if (!passwordEncoder.matches(mvo.getPassword(), membersVO.getPassword())) {
                dataVO.setSuccess(false);
                dataVO.setMessage("비밀번호가 일치하지 않습니다.");
                return dataVO;
            }

        String token = jwtUtil.generateToken(membersVO.getEmail());
        log.info("토큰 :",token);
        dataVO.setSuccess(true);
        dataVO.setMessage("로그인 성공");
        dataVO.setToken(token);
        dataVO.setData(Map.of(
            "member_id", membersVO.getMember_id(),
            "email", membersVO.getEmail(),
            "nickname", membersVO.getNickname(),
            "name", membersVO.getName(),
            "tel_no", membersVO.getTel_no()
        ));
        //dataVO.setData(membersVO);

        return dataVO;
    } catch (Exception e) {
        log.error("로그인 중 오류 발생: {}", e.getMessage());
        dataVO.setSuccess(false);
        dataVO.setMessage("로그인 오류 발생");
        return dataVO;
    }
}


@PutMapping("/update-nickname")
@ResponseBody
public ResponseEntity<?> updateNickname(@RequestBody Map<String, String> request) {
    System.out.println("닉네임 변경한다!!!! : " + request);
    String email = request.get("email");
    String newNickname = request.get("newNickname");

    try {
        // 닉네임 중복 체크
        if (!membersService.isNameAvailable(newNickname)) {
            return ResponseEntity.badRequest().body(Map.of("message", "닉네임이 이미 사용 중입니다.", "status", "failure"));
        }

        // 닉네임 업데이트
        int result = membersService.updateNickname(email, newNickname);
        if (result > 0) {
            return ResponseEntity.ok(Map.of("message", "닉네임이 성공적으로 변경되었습니다.", "status", "success"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "닉네임 변경 실패", "status", "failure"));
        }
    } catch (Exception e) {
        log.error("닉네임 변경 중 오류 발생: {}", e.getMessage());
        return ResponseEntity.badRequest().body(Map.of("message", "닉네임 변경 중 오류가 발생했습니다.", "status", "failure"));
    }
}




@PostMapping("/logout")
public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
    log.info("로그아웃 요청 데이터!!!!!!!!!!!!: {}", token);
    return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
}



// 마이페이지 이미지 변경  <?> => 와일드카드 (어떤 타입이라도 허용한다는 뜻)
@PostMapping("/upload-profile-image")
public ResponseEntity<?> uploadProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
    log.info("프로필 이미지 업로드 요청: 이메일 = {}, 파일 이름 = {}", email, file.getOriginalFilename());
    try {
        // 절대 경로로 파일 저장
        String absolutePath = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(absolutePath + fileName);

        // 디렉토리 생성
        Files.createDirectories(uploadPath.getParent());
        log.info("디렉토리 생성 확인: {}", uploadPath.getParent().toAbsolutePath());

        // 파일 저장
        Files.write(uploadPath, file.getBytes());
        log.info("파일 저장 완료: {}", uploadPath.toAbsolutePath());

        // DB 업데이트
        String imageUrl = "/uploads/" + fileName; // 상대 경로
        membersService.updateProfileImage(email, imageUrl);

        return ResponseEntity.ok(Map.of("status", "success", "imageUrl", imageUrl));
    } catch (Exception e) {
        log.error("파일 저장 중 오류 발생: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("status", "failure", "message", e.getMessage()));
    }
}


    //이미지 삭제
     @PostMapping("/delete-profile-image")
    public ResponseEntity<?> deleteProfileImage(@RequestParam("email") String email) {
        try {
            // 기본 이미지로 변경 (DB에서 이미지 경로를 null로 설정)
            membersService.updateProfileImage(email, null);
            return ResponseEntity.ok(Map.of("status", "success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "failure", "message", e.getMessage()));
        }
    }


    @GetMapping("/get-profile")
public ResponseEntity<?> getProfile(@RequestParam("email") String email) {
    try {
        MembersVO user = membersService.getMembersByIdEmail(email);
        if (user != null) {
            if (user.getProfile_image() != null && !user.getProfile_image().startsWith("http")) {
                String absoluteImagePath = "http://localhost:8080" + user.getProfile_image();
                user.setProfile_image(absoluteImagePath);
            }
            return ResponseEntity.ok(Map.of("success", true, "user", user));
        } else {
            return ResponseEntity.ok(Map.of("success", false, "message", "사용자를 찾을 수 없습니다."));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("success", false, "message", e.getMessage()));
    }
}


    @GetMapping("/userInfo")
    public ResponseEntity<?> hayooninfo(@RequestParam String email) {
        try {
            MembersVO membersVO = membersService.getMembersByIdEmail(email);
            if (membersVO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보가 없습니다.");
            }
            return ResponseEntity.ok(membersVO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러 발생");
        }
    }

    @PostMapping("/check-password")
    public ResponseEntity<Boolean> checkPassword(@RequestBody PasswordCheckRequest request) {
        try {
            // 이메일로 사용자 조회
            MembersVO member = membersService.getMembersByIdEmail(request.getEmail());

            if (member == null) {
                return ResponseEntity.ok(false); // 사용자 없음
            }

            // 비밀번호 검증
            boolean matches = passwordEncoder.matches(request.getOldPassword(), member.getPassword());
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            log.error("비밀번호 확인 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody MembersVO membersVO) {
        try {
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(membersVO.getPassword());
            membersVO.setPassword(encodedPassword);

            // 비밀번호 변경 로직 호출
            int updateCount = membersService.updatePassword(membersVO);

            if (updateCount > 0) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true); // 성공 여부
                response.put("message", "비밀번호가 성공적으로 변경되었습니다.");
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON) // Content-Type 명시
                        .body(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false); // 성공 여부
                response.put("message", "비밀번호 변경에 실패했습니다. 사용자 정보를 확인하세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "서버 오류로 인해 비밀번호를 변경할 수 없습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        }
    }

    @PostMapping("/updatePhone")
    public ResponseEntity<Map<String, Object>> updatePhone(@RequestBody MembersVO membersVO) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 휴대폰번호 변경 로직
            System.out.println("변경 요청된 휴대폰 번호: " + membersVO.getTel_no());
            int updateCount = membersService.updatePhone(membersVO);

            if (updateCount > 0) {
                response.put("success", true); // 성공 여부
                response.put("message", "휴대폰번호가 성공적으로 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false); // 성공 여부
                response.put("message", "휴대폰번호 변경에 실패했습니다. 사용자 정보를 확인하세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            response.put("success", false); // 성공 여부
            response.put("message", "서버 오류로 인해 휴대폰번호를 변경할 수 없습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/updateAdvAgree")
    public ResponseEntity<Map<String, Object>> updateAdvAgree(@RequestBody MembersVO membersVO) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 광고성 정보 수신 동의 상태 변경 로직
            System.out.println("변경 요청된 광고성 동의 상태: " + membersVO.getAdv_agree());
            int updateCount = membersService.updateAdvAgree(membersVO);

            if (updateCount > 0) {
                response.put("success", true); // 성공 여부
                response.put("message", "광고성 정보 수신 동의 상태가 성공적으로 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false); // 성공 여부
                response.put("message", "광고성 정보 수신 동의 상태 변경에 실패했습니다. 사용자 정보를 확인하세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            response.put("success", false); // 성공 여부
            response.put("message", "서버 오류로 인해 광고성 정보 수신 동의 상태를 변경할 수 없습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Map<String, Object>> withdrawMember(@RequestBody MembersVO membersVO) {
        Map<String, Object> response = new HashMap<>();

        try {
            // 실제 탈퇴 처리 로직 (DB 삭제 or status 변경 등) 
            int result = membersService.withdrawMember(membersVO);

            response.put("success", true);
            if (result>0) {
                response.put("message", "회원 탈퇴가 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "회원 탈퇴에 실패했습니다. 잠시 후 다시 시도해주세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "서버 오류로 인해 탈퇴가 실패하였습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /* 회원 상세 조회 */
    @GetMapping("/getmemberdetail")
  public DataVO getMemberDetail(@RequestParam("member_id") String member_id) {
    DataVO dataVO = new DataVO();
    try {
      MembersVO mvo = membersService.getMemberDetail(member_id);
      log.info("------------");
      log.info("mvo:", mvo.getEmail());
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(mvo);

    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }

    /* 판매 회원 상세 조회 */
    @GetMapping("/getpostmemberdetail")
  public DataVO getPostMemberDetail(@RequestParam("pwr_id") String pwr_id) {
    DataVO dataVO = new DataVO();
    try {
      MembersVO mvo = membersService.getPostMemberDetail(pwr_id);
      log.info("------------");
      log.info("mvo:", mvo.getEmail());
      dataVO.setSuccess(true);
      dataVO.setMessage("조회 성공");
      dataVO.setData(mvo);

    } catch (Exception e) {
      dataVO.setSuccess(false);
      dataVO.setMessage("조회 실패");
    }
    return dataVO;
  }





  @PostMapping("/find-email-by-phone")
  public ResponseEntity<?> findEmailByPhone(@RequestParam("phone") String phone) {
      log.info("요청 도착 - phone: {}", phone);
  
      try {
          // 데이터베이스에서 tel_no로 조회
          MembersVO member = membersService.findMemberByPhone(phone);
          log.info("멤버 받기: {}", member);
  
          if (member == null) {
              log.warn("휴대폰 번호를 찾을 수 없음: {}", phone);
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                  "message", "해당 번호로 가입된 이메일이 없습니다.",
                  "status", "failure"
              ));
          }
  
          // 이메일 일부 마스킹 처리 (예: a****@example.com)
          String email = member.getEmail();
          String maskedEmail = email.replaceAll("(?<=.{4}).(?=.*@)", "*");
          log.info("마스킹된 이메일: {}", maskedEmail);
  
          return ResponseEntity.ok(Map.of(
              "message", "이메일 찾기 성공",
              "email", maskedEmail,
              "status", "success"
          ));
      } catch (Exception e) {
          log.error("이메일 찾기 중 오류 발생: {}", e.getMessage());
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
              "message", "이메일 조회 중 오류가 발생했습니다.",
              "status", "failure"
          ));
      }
  }
  
}
