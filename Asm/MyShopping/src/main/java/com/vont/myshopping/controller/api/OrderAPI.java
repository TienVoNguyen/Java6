package com.vont.myshopping.controller.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.vont.myshopping.models.entity.Order;
import com.vont.myshopping.payload.response.OrderDetailResponse;
import com.vont.myshopping.payload.response.OrderResponse;
import com.vont.myshopping.payload.response.ProductResponse;
import com.vont.myshopping.service.IOrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/order")
public class OrderAPI {
    private final IOrderService orderService;

    public OrderAPI(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody JsonNode orderData) {
        return ResponseEntity.ok(orderService.save(orderData));
    }

    @GetMapping()
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<OrderResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "2") int size)
    {
        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("detail/{id}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDetailResponse>> getOrderDetail(@PathVariable(required = true) Long id)
    {
        return new ResponseEntity<>(orderService.findOrderDetail(id), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<?> updateOrder(@PathVariable(required = true) Long id, @RequestBody Order order)
    {
        return new ResponseEntity<>(orderService.save(order), HttpStatus.OK);
    }
}
