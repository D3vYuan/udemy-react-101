package com.maoluvcode.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maoluvcode.springbootlibrary.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
