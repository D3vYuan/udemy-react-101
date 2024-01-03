package com.maoluvcode.library.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.maoluvcode.library.dao.BookRepository;
import com.maoluvcode.library.dto.AddBookRequest;
import com.maoluvcode.library.entity.Book;

@Service
@Transactional
public class AdminService {
    private BookRepository bookRepository;

    public AdminService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void postBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);
    }
}
