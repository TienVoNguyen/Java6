package com.vont.myshopping.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Product extends BaseEntity{
    @NotBlank
    @Size(max = 100)
    @Column( length = 100)
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
    @Column( precision = 20)
    private BigDecimal price;

    @Column
    private Boolean available;

    @ManyToOne
    private Category category;
}