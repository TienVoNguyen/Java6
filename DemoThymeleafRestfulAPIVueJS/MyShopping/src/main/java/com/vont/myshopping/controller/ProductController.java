package com.vont.myshopping.controller;

import com.vont.myshopping.service.IProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("product")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productList(Model model, @RequestParam Optional<Long> cid) {
        if (cid.isPresent()) {
            model.addAttribute("products", productService.findByCategory(cid.get()));
        }else {
            Pageable pageable = PageRequest.of(0, 9);
            model.addAttribute("products", productService.findAllDto(pageable));
        }
        return "user/product/list";

    }

    @GetMapping("detail/{id}")
    public String productDetail(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("product", productService.findById(id));
        return "user/product/detail";
    }
}
