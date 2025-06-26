package com.sm.wifey.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("admin")
public class AdminController {

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
