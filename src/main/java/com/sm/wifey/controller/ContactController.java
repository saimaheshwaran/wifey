package com.sm.wifey.controller;

import com.sm.wifey.model.Contact;
import com.sm.wifey.repository.ContactRepository;
import com.sm.wifey.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("contact")
public class ContactController {

    @Autowired
    EmailService emailService;

    @Autowired
    ContactRepository contactRepository;

    @GetMapping
    public String getContact(HttpServletRequest request, Model model) {

        model.addAttribute("contactForm", new Contact());
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("title", "Contact");
        model.addAttribute("content", "contact");
        return "layout";

    }

    @PostMapping
    public String submitContactForm(@Valid @ModelAttribute("contactForm") Contact contactForm,
                                    BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "contact";
        }
        contactRepository.save(contactForm);
        //emailService.sendContactEmail(contactForm);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/contact";
    }
}
