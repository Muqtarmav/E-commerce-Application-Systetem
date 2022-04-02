package com.phoenix.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartUpdateDto {
    private Long userId;
    private Long itemId;

    private QuantityOperation quantityOperation;

}
