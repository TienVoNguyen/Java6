package com.vont.myshopping.models.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.vont.myshopping.models.entity.Product} entity
 */
@Data
public class ProductDto implements Serializable {
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 50)
    private String description;
    @NotBlank
    @Size(max = 50)
    private String image;
    @Min(1)
    @NotNull
    private Integer quantity;
    @NotNull
    private BigDecimal price;

    private Boolean available;

    private Long categoryId;
}