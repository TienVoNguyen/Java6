package com.vont.myshopping.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category extends BaseEntity{

    @NotBlank
    @Size(max = 20)
    @Column(length = 20, unique = true, nullable = false)
    private String code;

    @NotBlank
    @Size(max = 30)
    @Nationalized
    @Column( length = 30)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products;

}