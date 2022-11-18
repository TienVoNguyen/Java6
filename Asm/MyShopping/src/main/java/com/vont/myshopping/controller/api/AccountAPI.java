package com.vont.myshopping.controller.api;

import com.vont.myshopping.payload.request.SigninRequest;
import com.vont.myshopping.service.IAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("api")
public class AccountAPI {
    private final IAccountService accountService;

    public AccountAPI(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("account/autologin/{param}")
    public ResponseEntity<?> autoLogin(@PathVariable Integer param) {
        return accountService.autoLogin(param);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(accountService.authenticateUser(signinRequest));
    }
}
