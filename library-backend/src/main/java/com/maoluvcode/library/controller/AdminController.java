package com.maoluvcode.library.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maoluvcode.library.dto.AddBookRequest;
import com.maoluvcode.library.service.AdminService;
import com.maoluvcode.library.utils.ExtractJWT;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private AdminService adminService;
    private static final String USER_TYPE_FIELD = "\"userType\"";
    private static final String UNAUTHORIZED_EXCEPTION_MESSGAGE = "Administratino Page Only";

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQuantity(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId)
            throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, USER_TYPE_FIELD);
        if (admin == null || !admin.equals("admin")) {
            throw new Exception(UNAUTHORIZED_EXCEPTION_MESSGAGE);
        }

        adminService.decreaseBookQuantity(bookId);
    }

    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId)
            throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, USER_TYPE_FIELD);
        if (admin == null || !admin.equals("admin")) {
            throw new Exception(UNAUTHORIZED_EXCEPTION_MESSGAGE);
        }

        adminService.increaseBookQuantity(bookId);
    }

    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value = "Authorization") String token,
            @RequestBody AddBookRequest addBookRequest)
            throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, USER_TYPE_FIELD);
        if (admin == null || !admin.equals("admin")) {
            throw new Exception(UNAUTHORIZED_EXCEPTION_MESSGAGE);
        }
        adminService.postBook(addBookRequest);
    }

    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId)
            throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, USER_TYPE_FIELD);
        if (admin == null || !admin.equals("admin")) {
            throw new Exception(UNAUTHORIZED_EXCEPTION_MESSGAGE);
        }
        adminService.deleteBook(bookId);
    }

}
