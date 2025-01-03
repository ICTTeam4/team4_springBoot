package com.saintkream.server.domain.transaction.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionVO {
    private String idx,	trans_id, trans_price, trans_date, is_fixed, trans_method, trans_vill, is_payed, is_zub, pwr_id, buyer_id, seller_id;
}
