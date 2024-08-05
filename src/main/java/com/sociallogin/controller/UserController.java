package com.sociallogin.controller;

import com.sociallogin.auth.jwt.JwtService;
import com.sociallogin.dto.request.UserLoginRequest;
import com.sociallogin.dto.request.UserSignUpRequest;
import com.sociallogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(UserSignUpRequest signUpRequest) {
        userService.SignUp(signUpRequest);
        return "redirect:/users/login";
    }

    @PostMapping("/login")
    public String doLogin(UserLoginRequest userLoginRequest) {
        Long userId = userService.Login(userLoginRequest);
        String accessToken = jwtService.createToken(userId);
        // 이후 rest api로 변경 시 jwt 토큰을 response body에 담아서 반환
        return "redirect:/main";
    }
}
