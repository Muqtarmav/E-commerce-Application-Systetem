package com.phoenix.data.dto;

import com.phoenix.data.models.Item;
import lombok.Data;

import java.util.List;

@Data
public class CartRequestDto {

    private Long userId;
    private Long productId;
    private Integer quantity;

    //private List<Item> items;
}
