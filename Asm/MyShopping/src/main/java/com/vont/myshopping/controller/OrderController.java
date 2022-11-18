package com.vont.myshopping.controller;

import com.vont.myshopping.security.UserDetailsImpl;
import com.vont.myshopping.service.IOrderService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("order")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String orderList(Model model, HttpServletRequest request) {
        String username = request.getRemoteUser();
        model.addAttribute("orderList", orderService.findAllByUsername(username));
        return "user/order/list";
    }

    @GetMapping("checkout")
    public String checkout(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        model.addAttribute("name", userDetails.getFullName());
        return "user/order/checkout";
    }

    @GetMapping("detail/{id}")
    public String orderDetail(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("order", orderService.findById(id));
        return "user/order/detail";
    }
}
