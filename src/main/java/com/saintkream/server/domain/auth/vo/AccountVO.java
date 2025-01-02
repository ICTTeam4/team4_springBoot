package com.saintkream.server.domain.auth.vo;

import lombok.Data;

@Data
public class AccountVO {
    private Long id;
    private Long userId;
    private String bankName;
    private String accountNumber;
    private String accountHolderName;
    private boolean isDefault;
}
