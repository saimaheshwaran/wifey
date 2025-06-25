package com.sm.newswave.controller;

import com.sm.newswave.model.User;
import com.sm.newswave.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getRegister(Model model) {
        model.addAttribute("register", new User());
        return "register";
    }

    @PostMapping
    public String registerUser(
            @Valid @ModelAttribute("register") User user,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Registration successful! You can now login.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }

}
