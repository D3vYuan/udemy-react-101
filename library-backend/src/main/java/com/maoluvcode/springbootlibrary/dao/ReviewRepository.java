package com.maoluvcode.springbootlibrary.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maoluvcode.springbootlibrary.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
