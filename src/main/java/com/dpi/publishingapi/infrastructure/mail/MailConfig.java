package com.dpi.publishingapi.infrastructure.mail;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    @Value("${MAILJET_API_KEY}")
    private String mailJetApiKey;

    @Value("${MAILJET_SECRET_KEY}")
    private String mailJetSecretKey;

    @Bean
    public MailjetClient mailjetClient() {
        ClientOptions options = ClientOptions.builder()
                .apiKey(mailJetApiKey)
                .apiSecretKey(mailJetSecretKey)
                .build();
        return new MailjetClient(options);
    }
}
