package com.saintkream.server.domain.address.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saintkream.server.domain.address.mapper.AddressMapper;
import com.saintkream.server.domain.auth.vo.AddressVO;

@Service
public class AddressServiceImpl implements AddressService {



    @Autowired
    private AddressMapper addressMapper;

    // @Override
    // public List<AddressVO> getAddressList(Long memberId) {
    //     return addressMapper.getAddressList(memberId);
    // }


    @Override
    public void addAddress(AddressVO addressVO) {
        addressMapper.insertAddress(addressVO);
    }

    @Override
    public void updateAddress(Long id, AddressVO addressVO) {
        addressVO.setId(id);
        addressMapper.updateAddress(addressVO);
    }

    @Override
    public void deleteAddress(Long id) {
        addressMapper.deleteAddress(id);
    }
    @Override
    public void setDefaultAddress(Long id, Long memberId) {
        // 기존 기본 배송지 초기화
        addressMapper.clearDefaultAddress(memberId);
        // 새로운 기본 배송지 설정
        addressMapper.setDefaultAddress(id);
    }

    @Override
    public List<AddressVO> getAddressList(Long userId) {
        return addressMapper.getAddressList(userId);
    }

    
}

