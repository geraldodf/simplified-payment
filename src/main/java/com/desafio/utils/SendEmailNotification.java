package com.desafio.utils;

import com.desafio.models.User;
import org.springframework.web.client.RestTemplate;

public class SendEmailNotification {
    public static void sendEmailNotification(String urlMock, User user, String msg) {
        RestTemplate restTemplate = new RestTemplate();
        MockResponse resposta = restTemplate.getForObject(urlMock, MockResponse.class);
    }
}
