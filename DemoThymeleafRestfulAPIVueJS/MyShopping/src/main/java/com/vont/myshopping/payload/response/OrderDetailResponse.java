package com.vont.myshopping.payload.response;

import com.vont.myshopping.models.entity.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetailResponse {
    private String productImage;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private int quantity;
}
