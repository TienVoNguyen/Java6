package com.vont.myshopping.service;

import com.vont.myshopping.models.dto.ProductDto;
import com.vont.myshopping.models.entity.Product;
import com.vont.myshopping.payload.request.ProductRequest;
import com.vont.myshopping.payload.response.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductDto save(ProductRequest productFormInput);

    ProductDto update(ProductRequest productFormInput);
    void delete(Long id);
    ProductDto findById(Long id);
    List<ProductDto> findByCategory(Long id);
    ProductResponse findAll(Pageable pageable);

    List<ProductDto> findAllDto(Pageable pageable);

    List<ProductDto> findAll();

    void deleteAll(long[] ids);
}
