package com.vont.myshopping.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vont.myshopping.models.entity.Order;
import com.vont.myshopping.models.entity.OrderDetail;
import com.vont.myshopping.payload.response.OrderDetailResponse;
import com.vont.myshopping.payload.response.OrderResponse;
import com.vont.myshopping.repository.OrderDetailRepository;
import com.vont.myshopping.repository.OrderRepository;
import com.vont.myshopping.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;



    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public Order save(Order order) {
        order.setStatus(true);
        return orderRepository.save(order);
    }

    @Override
    public Order save(JsonNode orderData) {
        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.convertValue(orderData, Order.class);
        order.setStatus(false);
        orderRepository.save(order);

        TypeReference<List<OrderDetail>> type = new TypeReference<List<OrderDetail>>() {};
        List<OrderDetail> details = objectMapper.convertValue(orderData.get("orderDetail"), type)
                .stream().peek(d -> d.setOrder(order)).collect(Collectors.toList());
        orderDetailRepository.saveAll(details);
        return order;
    }

    @Override
    public void delete(long[] ids) {

    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> findAllByUsername(String username) {
        return orderRepository.findAllByCreateBy(username);
    }

    @Override
    public OrderResponse findAll(Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(pageable);
        OrderResponse orderFormOutput = new OrderResponse();
        orderFormOutput.setTotalItems(orderPage.getTotalElements());
        orderFormOutput.setTotalPages(orderPage.getTotalPages());
        orderFormOutput.setCurrentPage(orderPage.getNumber());
        orderFormOutput.setOrders(orderPage.getContent());
        return orderFormOutput;
    }

    @Override
    public List<OrderDetailResponse> findOrderDetail(Long id) {
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        List<OrderDetail> orders = orderDetailRepository.findAllByOrder_Id(id);
        orders.forEach(o -> {
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
            orderDetailResponse.setProductImage(o.getProduct().getImage());
            orderDetailResponse.setProductName(o.getProduct().getName());
            orderDetailResponse.setPrice(o.getPrice());
            orderDetailResponse.setProductDescription(o.getProduct().getDescription());
            orderDetailResponse.setQuantity(o.getQuantity());
            orderDetailResponses.add(orderDetailResponse);
        });
        return orderDetailResponses;
    }
}
