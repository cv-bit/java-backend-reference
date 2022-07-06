package com.dpi.publishingapi.features.payment;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PaymentConfig {

    @Value("${paypal.clientId}")
    private String paypalClientId;

    @Value("${paypal.secret}")
    private String paypalSecret;


    @Bean
    PayPalHttpClient paypalClient() {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
                paypalClientId,
                paypalSecret
        );

        return new PayPalHttpClient(environment);
    }

}
