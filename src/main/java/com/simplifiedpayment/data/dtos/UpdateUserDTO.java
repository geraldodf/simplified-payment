package com.simplifiedpayment.data.dtos;

import com.simplifiedpayment.data.models.User;

public record UpdateUserDTO(
        String name,
        String email,
        String document,
        String type,
        Double wallet
) {
    public User toUser() {
        User user = new User();

        if (isValid(this.name)) {
            user.setName(this.name);
        }
        if (isValid(this.email)) {
            user.setEmail(this.email);
        }
        if (isValid(this.document)) {
            user.setDocument(this.document);
        }
        if (isValid(this.type)) {
            user.setType(this.type);
        }
        if (null != this.wallet && this.wallet > 0) {
            user.setWallet(this.wallet);
        }

        return user;
    }

    public User updateUser(User existingUser) {
        if (isValid(this.name)) {
            existingUser.setName(this.name);
        }
        if (isValid(this.email)) {
            existingUser.setEmail(this.email);
        }
        if (isValid(this.document)) {
            existingUser.setDocument(this.document);
        }
        if (isValid(this.type)) {
            existingUser.setType(this.type);
        }
        if (null != this.wallet && this.wallet > 0) {
            existingUser.setWallet(this.wallet);
        }

        return existingUser;
    }

    private boolean isValid(String str) {
        return str != null && !str.isEmpty();
    }
}
