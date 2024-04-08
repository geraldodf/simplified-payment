package com.desafio.notification;

import com.desafio.data.models.User;
import com.desafio.utils.MockResponse;
import org.springframework.web.client.RestTemplate;

public class SendEmailNotification implements NotificationSender {
    @Override
    public void sendNotification(String url, User user, String msg) {
        RestTemplate restTemplate = new RestTemplate();
        MockResponse resposta = restTemplate.getForObject(url, MockResponse.class);

        System.out.println("Resposta do provedor externo... Foi aprovado? " + resposta.getMessage());
        System.out.println(msg + " por: " + user.getName());
        System.out.println("Enviando e-mail de notificação para: " + user.getName());
    }
}
