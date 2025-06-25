package com.sm.newswave.controller;

import com.sm.newswave.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("login")
public class LoginController {

    @GetMapping
    public String getLogin(
        HttpServletRequest request,
        Model model,
        @RequestParam(value = "logout", required = false) String logout,
        @RequestParam(value = "error", required = false) String error) {

            // Add an empty LoginForm object to bind to the form
            model.addAttribute("user", new User());

            // Check for logout
            if (logout != null) {
                model.addAttribute("successMessage", "You have been logged out successfully.");
            }

            // Check for login error
            if (error != null) {
                HttpSession session = request.getSession(false);
                String errorMessage = null;
                if (session != null) {
                    AuthenticationException ex = (AuthenticationException) session
                            .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
                    if (ex != null) {
                        errorMessage = ex.getMessage();
                    }
                }
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "Invalid username or password!";
                }
                model.addAttribute("errorMessage", errorMessage);
            }

            return "login"; // Make sure this matches your template path
    }

}
