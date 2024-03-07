package com.desafio.utils;

import org.springframework.web.client.RestTemplate;

public class ObterMock {
    static public MockResponse obterMensagemDoMock(String urlMock) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(urlMock, MockResponse.class);
    }
}
