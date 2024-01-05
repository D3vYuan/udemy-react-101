package com.maoluvcode.library.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maoluvcode.library.dao.PaymentRepository;
import com.stripe.Stripe;

@Service
@Transactional
public class PaymentService {
    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

}
