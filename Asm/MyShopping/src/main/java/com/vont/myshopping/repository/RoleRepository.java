package com.vont.myshopping.repository;

import com.vont.myshopping.models.entity.ERole;
import com.vont.myshopping.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}