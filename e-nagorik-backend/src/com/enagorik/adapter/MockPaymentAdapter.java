package com.enagorik.adapter;
import java.util.UUID;
public final class MockPaymentAdapter implements PaymentAdapter { public PaymentResult pay(int amount,String reference){if(amount<0)return new PaymentResult(false,"","Invalid amount");return new PaymentResult(true,"TX-"+UUID.randomUUID(),"Payment accepted");} }
