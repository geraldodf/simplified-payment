package com.desafio.dtos;

import com.desafio.models.User;
import jakarta.validation.constraints.*;

public record CreateUserDTO(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @NotBlank(message = "Document cannot be blank")
        String document,

        @NotNull(message = "Type cannot be null")
        @NotBlank(message = "Email cannot be blank")
        String type,

        @Positive
        @NotNull
        Double wallet


) {
    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setDocument(this.document);
        user.setType(this.type);
        user.setWallet(this.wallet);

        return user;
    }
}
