package com.enagorik.adapter;
public interface PaymentAdapter { PaymentResult pay(int amount,String reference); record PaymentResult(boolean successful,String transactionId,String message){} }
