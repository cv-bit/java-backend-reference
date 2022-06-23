package com.dpi.publishingapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class CustomConfiguration {

    @Value("${MAILJET_API_KEY}")
    private String mailJetApiKey;

    @Value("${MAILJET_SECRET_KEY}")
    private String mailJetSecretKey;


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public MailjetClient mailjetClient() {
        ClientOptions options = ClientOptions.builder()
                .apiKey(mailJetApiKey)
                .apiSecretKey(mailJetSecretKey)
                .build();
        return new MailjetClient(options);
    }
}
