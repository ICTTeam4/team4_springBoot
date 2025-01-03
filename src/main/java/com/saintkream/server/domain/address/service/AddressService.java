package com.saintkream.server.domain.address.service;

import java.util.List;

import com.saintkream.server.domain.auth.vo.AddressVO;

public interface AddressService {
    void addAddress(AddressVO addressVO);
    void updateAddress(String id, AddressVO addressVO);
    void deleteAddress(String id);
    void setDefaultAddress(String id, String userId);
    List<AddressVO> getAddressList(String userId);
}
