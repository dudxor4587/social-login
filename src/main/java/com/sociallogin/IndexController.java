package com.sociallogin;

import com.sociallogin.auth.Auth;
import com.sociallogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private final UserService userService;

    @GetMapping("/main")
    public String index(Model model) {
        String username = userService.getUserNameById(2L);
        model.addAttribute("username", username);
        return "index";
    }
}
