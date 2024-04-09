package com.simplifiedpayment.data.dtos;

import com.simplifiedpayment.data.models.User;

public record ReadUserDTO(Long id, String name, String email, String document, String type, Double wallet) {

    public static ReadUserDTO userToReadDTO(User user) {
        return new ReadUserDTO(user.getId(), user.getName(), user.getEmail(), user.getDocument(), user.getType(), user.getWallet());
    }
}
