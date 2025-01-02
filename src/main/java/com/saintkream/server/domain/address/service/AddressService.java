package com.saintkream.server.domain.address.service;

import java.util.List;

import com.saintkream.server.domain.auth.vo.AddressVO;

public interface AddressService {
    void addAddress(AddressVO addressVO);
    void updateAddress(Long id, AddressVO addressVO);
    void deleteAddress(Long id);
    void setDefaultAddress(Long id, Long userId);
    List<AddressVO> getAddressList(Long userId);
}
