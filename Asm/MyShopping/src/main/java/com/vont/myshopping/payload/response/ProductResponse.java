package com.vont.myshopping.payload.response;

import com.vont.myshopping.models.dto.ProductDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private Long totalItems;
    private int totalPages;
    private int currentPage;
    private List<ProductDto> productDtoList;
}
