package com.vont.myshopping.controller;

import com.vont.myshopping.payload.request.SignupRequest;
import com.vont.myshopping.security.UserDetailsImpl;
import com.vont.myshopping.security.jwt.JwtUtils;
import com.vont.myshopping.service.IAccountService;
import com.vont.myshopping.utils.AutoLoginUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("account")
public class AccountController {
    private final IAccountService accountService;
    private final JwtUtils jwtUtils;
    public AccountController(IAccountService accountService, JwtUtils jwtUtils) {
        this.accountService = accountService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("login/form")
    public String loginForm(Model model) {
        model.addAttribute("message", "Vui lòng đăng nhập!");
        return "account/login";
    }

    @GetMapping("autologin")
    public String autoLoginRedirect() {
        AutoLoginUtils.param = (int) (Math.random() * (999999999 - 111111111 + 1) + 111111111);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AutoLoginUtils.jwt = jwtUtils.generateJwtToken(authentication);
        AutoLoginUtils.userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return "redirect:http://localhost:8080/autologin/" + AutoLoginUtils.param;
    }



    @GetMapping("login/success")
    public String loginSuccess(Model model) {
        model.addAttribute("message", "Đăng nhập thành công");
        return "account/login";
    }

    @GetMapping("login/error")
    public String loginError(Model model) {
        model.addAttribute("message", "Lỗi đăng nhập");
        return "account/login";
    }


    @GetMapping("logout/success")
    public String logoffSuccess(Model model) {
        model.addAttribute("message", "Đăng xuất thành công");
        return "account/login";
    }

    @GetMapping("logoff/unauthoried")
    public String unAuthoried(Model model) {
        model.addAttribute("message", "Bạn không có quyền truy xuất");
        return "account/login";
    }

    @PostMapping("/signup")
    public String registerUser(Model model, @Valid @ModelAttribute SignupRequest signUpRequest) {
        model.addAttribute("message", accountService.registerUser(signUpRequest).getMessage());
        return "account/login";
    }
}
