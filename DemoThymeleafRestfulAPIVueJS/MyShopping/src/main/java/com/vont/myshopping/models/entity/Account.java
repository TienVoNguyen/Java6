package com.vont.myshopping.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account extends BaseEntity {

    @NotBlank
    @Column(length = 20, unique = true, nullable = false)
    private String username;

    @NotBlank
    @Column(length = 130, nullable = false)
    private String password;

    @NotBlank
    @Size(max = 30)
    @Column(name = "fullname",length = 100)
    private String fullName;



    @NotBlank
    @Email
    private String email;

    @Column
    private Boolean gender;

    @Past
    private Date dob;

    @NotBlank
    @Size(max = 100)
    private String address;

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
    @Column(length = 12)
    private String phoneNumber;

    @Column
    private Boolean status;

    @ManyToMany
    @JoinTable(name = "account_role", joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();


}