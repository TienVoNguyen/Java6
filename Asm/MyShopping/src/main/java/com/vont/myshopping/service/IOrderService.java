package com.vont.myshopping.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.vont.myshopping.models.entity.Order;
import com.vont.myshopping.payload.response.OrderDetailResponse;
import com.vont.myshopping.payload.response.OrderResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order save(Order order);
    void delete(long[] ids);
    Order findById(Long id);
//    List<Order> findByCategory(Long id);

    Order save(JsonNode orderData);

    List<Order> findAllByUsername(String username);

    OrderResponse findAll(Pageable pageable);

    List<OrderDetailResponse> findOrderDetail(Long id);
}
