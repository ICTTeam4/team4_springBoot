package com.saintkream.server.domain.address.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.saintkream.server.domain.auth.vo.AddressVO;

@Mapper
public interface AddressMapper {
    void insertAddress(AddressVO addressVO);
    void updateAddress(AddressVO addressVO);
    void deleteAddress(Long id);

    // 기본 배송지 처리
    void clearDefaultAddress(Long memberId);  // ← 파라미터 이름을 바꾼다면 XML도 동일하게 수정해야 함
    void setDefaultAddress(Long id);

    // 주소 목록 조회
    List<AddressVO> getAddressList(Long memberId);  // ← XML에서도 id="getAddressList"로
}
