package com.maoluvcode.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maoluvcode.library.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByUserEmail(String userEmail);
}
