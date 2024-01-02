package com.maoluvcode.library.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maoluvcode.library.entity.Book;
import com.maoluvcode.library.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/ischeckout/byuser")
    public Boolean checkoutBookByUser(@RequestParam Long bookId) {
        String email = "testuser@email.com";
        return bookService.checkoutBookByUser(email, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book checkoutBook(@RequestParam Long bookId) throws Exception {
        String userEmail = "testuser@email.com";
        return bookService.checkoutBook(userEmail, bookId);
    }

}
