package com.vont.myshopping.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "`order`")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order extends BaseEntity{

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Column
    private String fullName;

    @Column
    private Boolean status;

    @Size(max = 12)
    @Column(name = "phoneNumber", length = 12)
    private String phoneNumber;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<OrderDetail> orderDetails;
}