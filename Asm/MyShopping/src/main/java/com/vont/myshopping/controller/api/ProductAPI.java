package com.vont.myshopping.controller.api;

import com.vont.myshopping.models.dto.ProductDto;
import com.vont.myshopping.payload.request.ProductRequest;
import com.vont.myshopping.payload.response.ProductResponse;
import com.vont.myshopping.service.IProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("api/product")
public class ProductAPI {
    private final IProductService productService;

    public ProductAPI(IProductService productService) {
        this.productService = productService;
    }
    @GetMapping("{id}")
    public ProductDto getOne (@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping()
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "2") int size)
    {
        Pageable pageable = PageRequest.of(page, size);

        return new ResponseEntity<ProductResponse>(productService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<?> save(@Valid @ModelAttribute ProductRequest productFormInput) {
        return ResponseEntity.ok(productService.save(productFormInput));
    }

    @PutMapping(value = "update/{id}")
    public ProductDto update(@ModelAttribute ProductRequest productFormInput, @PathVariable Long id) {
        productFormInput.setId(id);
        return productService.update(productFormInput);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete (@RequestBody long[] ids) {
        productService.deleteAll(ids);
        return ResponseEntity.ok("true");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok("true");
    }
}
