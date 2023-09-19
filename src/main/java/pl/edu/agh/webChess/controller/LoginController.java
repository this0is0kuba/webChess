package pl.edu.agh.webChess.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "authentication/login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "authentication/access-denied";
    }
}
