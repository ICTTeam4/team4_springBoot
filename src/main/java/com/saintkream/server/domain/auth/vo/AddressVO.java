package com.saintkream.server.domain.auth.vo;

import lombok.Data;

@Data
public class AddressVO {
    private  String id;          // add_id
    private String memberId;    // member_id
    private String name;      // name
    private String phone;     // phone
    private String zipcode;   // zipcode
    private String address;   // address
    private String detailAddress;  // detail_address
    private Boolean isDefault;     // is_default
}

