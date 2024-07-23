package com.balki.service;

import com.balki.model.Order;
import com.balki.response.PaymentResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
