package com.vont.myshopping.service;

import com.vont.myshopping.models.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail save(OrderDetail orderDetail);
    void delete(long[] ids);
    OrderDetail findById(Long id);
//    List<OrderDetail> findByCategory(Long id);
    List<OrderDetail> findAll();
}
