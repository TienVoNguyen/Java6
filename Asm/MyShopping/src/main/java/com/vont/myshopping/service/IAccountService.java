package com.vont.myshopping.service;

import com.vont.myshopping.models.entity.Account;
import com.vont.myshopping.payload.request.SigninRequest;
import com.vont.myshopping.payload.request.SignupRequest;
import com.vont.myshopping.payload.response.JwtResponse;
import com.vont.myshopping.payload.response.MessageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account save(Account account);
    void delete(long[] ids);
    Account findById(Long id);
//    List<Account> findByCategory(Long id);
    List<Account> findAll(Pageable pageable);

    Optional<Account> findByUsername(String username);

    MessageResponse registerUser(SignupRequest signUpRequest);

    ResponseEntity<?> autoLogin(int param);

    JwtResponse authenticateUser(SigninRequest signinRequest);
}
