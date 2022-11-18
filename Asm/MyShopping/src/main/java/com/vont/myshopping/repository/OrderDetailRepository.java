package com.vont.myshopping.repository;

import com.vont.myshopping.models.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    void deleteOrderDetailsByProduct_Id(Long id);

    List<OrderDetail> findAllByOrder_Id(Long id);
}