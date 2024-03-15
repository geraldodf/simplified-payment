package com.desafio.services;

import com.desafio.dtos.CreateUserDTO;
import com.desafio.dtos.UpdateUserDTO;
import com.desafio.models.Transfer;
import com.desafio.models.User;
import com.desafio.repositories.UserRepository;

import static io.micrometer.common.util.StringUtils.isNotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<ArrayList<User>> getAll() {
        try {
            ArrayList<User> users = (ArrayList<User>) this.userRepository.findAll();
            if (!users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    public ResponseEntity<User> getOneById(Long id) {
        try {
            Optional<User> optionalUser = this.userRepository.findById(id);
            boolean isPresent = optionalUser.isPresent();
            User user = isPresent ? optionalUser.get() : null;
            if (isPresent) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<User> create(CreateUserDTO userDto) {
        try {
            User user = userDto.toUser();
            User savedUser = this.userRepository.save(user);
            return ResponseEntity.status(201).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<Void> update(Long id, UpdateUserDTO userDTO) {
        User user = this.getOneById(id).getBody();
        if (isNotBlank(userDTO.getName())) {
            user.setName(userDTO.getName());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(null);
    }

    public ResponseEntity<Void> delete(Long id) {
        try {
            User userToDelete = Objects.requireNonNull(this.getOneById(id).getBody());
            this.userRepository.delete(userToDelete);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public void updateWallet(User user) {
        User userToUpdate = getOneById(user.getId()).getBody();
        userToUpdate.setWallet(user.getWallet());
        userRepository.save(userToUpdate);
    }
}
