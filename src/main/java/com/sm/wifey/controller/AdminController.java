package com.sm.wifey.controller;

import com.sm.wifey.service.BlogCommentService;
import com.sm.wifey.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    ContactService contactService;

    @Autowired
    BlogCommentService blogCommentService;

    @GetMapping
    public String getAdmin(HttpServletRequest request, Model model) {
        model.addAttribute("title", "Admin");
        model.addAttribute("content", "admin/index");
        model.addAttribute("requestURI", request.getRequestURI());
        return "admin/layout";
    }

    @GetMapping("contact")
    public String getContacts(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {

        model.addAttribute("title", "Admin/Contacts");
        model.addAttribute("content", "admin/contact");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("contactPage",
                contactService.contactPageRequest(page, 10));
        return "admin/layout";
    }

    @GetMapping("comment")
    public String getComments(@RequestParam(defaultValue = "0") int page, Model model, HttpServletRequest request) {

        model.addAttribute("title", "Admin/Comments");
        model.addAttribute("content", "admin/comment");
        model.addAttribute("requestURI", request.getRequestURI());
        model.addAttribute("commentPage",
                blogCommentService.commentPageRequest(page, 10));
        return "admin/layout";
    }

    @GetMapping("download-db")
    public ResponseEntity<Resource> downloadDatabase() throws IOException {
        File file = new File("./src/main/resources/static/database/data.mv.db");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        String dateTime = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename="+ dateTime +"_data.mv.db")
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
