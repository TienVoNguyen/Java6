package com.vont.myshopping.payload.response;

import com.vont.myshopping.models.dto.ProductDto;
import com.vont.myshopping.models.entity.Order;
import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private Long totalItems;
    private int totalPages;
    private int currentPage;
    private List<Order> orders;
}
