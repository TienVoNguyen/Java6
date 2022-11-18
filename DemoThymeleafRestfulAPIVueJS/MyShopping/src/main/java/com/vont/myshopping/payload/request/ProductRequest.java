package com.vont.myshopping.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 20)
    private String name;

    private MultipartFile image;

    @NotEmpty
    private String description;

    @NotNull
    private Long categoryId;

    @Min(1)
    @NotNull
    private Integer quantity;

    private Boolean available;

    @NotNull
    @Column( precision = 20)
    private BigDecimal price;
}
