package com.simplifiedpayment.services;

import com.simplifiedpayment.data.dtos.CreateUserDTO;
import com.simplifiedpayment.data.dtos.ReadUserDTO;
import com.simplifiedpayment.data.dtos.UpdateUserDTO;
import com.simplifiedpayment.data.models.User;
import com.simplifiedpayment.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ReadUserDTO> getAll() {
        return this.userRepository.findAll().parallelStream()
                .map(ReadUserDTO::userToReadDTO)
                .collect(Collectors.toList());
    }

    public User getOneById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        boolean isPresent = optionalUser.isPresent();
        return isPresent ? optionalUser.get() : null;
    }

    public User create(CreateUserDTO userDto) {
        User user = userDto.toUser();
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
        return this.userRepository.save(user);
    }

    public User update(Long id, UpdateUserDTO userDTO) {
        User user = this.getOneById(id);
        if (user == null) return null;
        User userToSave = userDTO.updateUser(user);
        return this.userRepository.save(userToSave);
    }

    public boolean delete(Long id) {
        User userToDelete = this.getOneById(id);
        if (userToDelete == null) return false;
        this.userRepository.delete(userToDelete);
        return true;
    }

    public void updateWallet(User user) {
        User userToUpdate = getOneById(user.getId());
        userToUpdate.setWallet(user.getWallet());
        userRepository.save(userToUpdate);
    }

    public User getOneByDocument(String document) {
        Optional<User> optionalUser = this.userRepository.findByDocument(document);
        boolean isPresent = optionalUser.isPresent();
        return isPresent ? optionalUser.get() : null;
    }

    public UserDetails getOneByEmail(String document) {
        return this.userRepository.findByEmail(document);
    }
}
