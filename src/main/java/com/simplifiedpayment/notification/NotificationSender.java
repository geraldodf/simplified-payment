package com.simplifiedpayment.notification;

import com.simplifiedpayment.data.models.User;

public interface NotificationSender {

    void sendNotification(String url, User user, String msg);
}
