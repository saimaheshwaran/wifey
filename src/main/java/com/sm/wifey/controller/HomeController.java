package com.sm.wifey.controller;

import com.sm.wifey.service.BlogPostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    BlogPostService blogPostService;

    @GetMapping
    public String getHome(HttpServletRequest request, Model model) {
        model.addAttribute("title", "Home");
        model.addAttribute("content", "home");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("recentPosts", blogPostService.blogPostPageRequest(0, 3));
        return "layout";
    }
}
