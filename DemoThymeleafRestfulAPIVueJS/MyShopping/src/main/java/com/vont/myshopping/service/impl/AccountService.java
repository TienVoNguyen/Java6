package com.vont.myshopping.service.impl;

import com.vont.myshopping.models.entity.Account;
import com.vont.myshopping.models.entity.ERole;
import com.vont.myshopping.models.entity.Role;
import com.vont.myshopping.payload.request.SigninRequest;
import com.vont.myshopping.payload.request.SignupRequest;
import com.vont.myshopping.payload.response.JwtResponse;
import com.vont.myshopping.payload.response.MessageResponse;
import com.vont.myshopping.repository.AccountRepository;
import com.vont.myshopping.repository.RoleRepository;
import com.vont.myshopping.security.UserDetailsImpl;
import com.vont.myshopping.security.jwt.JwtUtils;
import com.vont.myshopping.service.IAccountService;
import com.vont.myshopping.utils.AutoLoginUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    public AccountService(AccountRepository accountRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.accountRepository = accountRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public void delete(long[] ids) {

    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public List<Account> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<?> autoLogin(int param) {
        if(AutoLoginUtils.param == param) {
            List<String> roles = AutoLoginUtils.userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(AutoLoginUtils.jwt,
                    AutoLoginUtils.userDetails.getId(),
                    AutoLoginUtils.userDetails.getUsername(),
                    AutoLoginUtils.userDetails.getEmail(),
                    roles));
        }
        return ResponseEntity.accepted().body(new MessageResponse("loi dang nhap"));
    }

    @Override
    public JwtResponse authenticateUser(SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public MessageResponse registerUser(SignupRequest signUpRequest) {
        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        // Create new user's account
        Account user = Account.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .fullName(signUpRequest.getFullName())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .dob(signUpRequest.getDob())
                .address(signUpRequest.getAddress())
                .gender(signUpRequest.getGender())
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "staff" -> {
                        Role modRole = roleRepository.findByName(ERole.ROLE_STAFF)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        user.setRoles(roles);
        accountRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }
}
