package com.vont.myshopping.repository;

import com.vont.myshopping.models.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}