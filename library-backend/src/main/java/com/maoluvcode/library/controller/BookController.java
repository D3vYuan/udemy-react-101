package com.maoluvcode.library.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maoluvcode.library.dto.ShelfCurrentLoansResponse;
import com.maoluvcode.library.entity.Book;
import com.maoluvcode.library.service.BookService;
import com.maoluvcode.library.utils.ExtractJWT;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
@Slf4j
public class BookController {
    private BookService bookService;
    private static final String SUB_FIELD = "\"sub\"";

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader("Authorization") String token) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, SUB_FIELD);
        return bookService.currentLoans(userEmail);
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, SUB_FIELD);
        log.info("Found: {}", userEmail);
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId) {
        String email = ExtractJWT.payloadJWTExtraction(token, SUB_FIELD);
        return bookService.checkoutBookByUser(email, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestHeader(value = "Authorization") String token, @RequestParam Long bookId)
            throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, SUB_FIELD);
        return bookService.checkoutBook(userEmail, bookId);
    }

}
