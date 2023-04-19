package com.example.paymenttest.service;

import com.example.paymenttest.dto.checkout.CheckoutItemDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Value("${stripe.api.key}")
    private String apiKey;
    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        String successURL = "http://localhost:4040/payment/success";

        String failureURL = "http://localhost:4040/payment/failed";

        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        for(CheckoutItemDto checkoutItemDto : checkoutItemDtoList){
            sessionItemList.add(createSeassionLineItem(checkoutItemDto));
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successURL)
                .setCancelUrl(failureURL)
                .addAllLineItem(sessionItemList)
                .build();
        return Session.create(params);
    }

    private SessionCreateParams.LineItem createSeassionLineItem(CheckoutItemDto checkoutItemDto) {

        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkoutItemDto))
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("kzt")
                .setUnitAmount((long)checkoutItemDto.getPrice()*100)
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build()
                ).build();
    }
}
