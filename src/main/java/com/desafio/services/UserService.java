package com.desafio.services;

import com.desafio.data.dtos.CreateUserDTO;
import com.desafio.data.dtos.UpdateUserDTO;
import com.desafio.data.models.User;
import com.desafio.repositories.UserRepository;

import static io.micrometer.common.util.StringUtils.isNotBlank;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        List<User> users = this.userRepository.findAll();
        return users;
    }

    public User getOneById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        boolean isPresent = optionalUser.isPresent();
        User user = isPresent ? optionalUser.get() : null;
        return user;
    }

    public User create(CreateUserDTO userDto) {
        User user = userDto.toUser();
        User savedUser = this.userRepository.save(user);
        return user;
    }

    public void update(Long id, UpdateUserDTO userDTO) {
        User user = this.getOneById(id);
        if (isNotBlank(userDTO.name())) {
            user.setName(userDTO.name());
        }
    }

    public void delete(Long id) {
        User userToDelete = Objects.requireNonNull(this.getOneById(id));
        this.userRepository.delete(userToDelete);
    }

    public void updateWallet(User user) {
        User userToUpdate = getOneById(user.getId());
        userToUpdate.setWallet(user.getWallet());
        userRepository.save(userToUpdate);
    }
}
