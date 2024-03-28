package com.desafio.notification;

import com.desafio.data.models.User;

public interface NotificationSender {

    void sendNotification(String url, User user, String msg);
}
