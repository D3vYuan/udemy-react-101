package com.maoluvcode.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maoluvcode.library.dto.AddBookRequest;
import com.maoluvcode.library.service.AdminService;
import com.maoluvcode.library.utils.ExtractJWT;

@CrossOrigin("http://localhost:3000")
@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;
    private static final String USER_TYPE_FIELD = "\"userType\"";

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader("Authorization") String token, @RequestBody AddBookRequest addBookRequest)
            throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, USER_TYPE_FIELD);
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.postBook(addBookRequest);
    }

}
