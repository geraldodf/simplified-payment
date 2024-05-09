package com.simplifiedpayment.services;

import com.simplifiedpayment.data.dtos.CreateUserDTO;
import com.simplifiedpayment.data.dtos.ReadUserDTO;
import com.simplifiedpayment.data.dtos.UpdateUserDTO;
import com.simplifiedpayment.data.models.User;
import com.simplifiedpayment.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<ReadUserDTO> getAll() {
        logger.info("Buscando todos os usuários.");
        return this.userRepository.findAll().parallelStream()
                .map(ReadUserDTO::userToReadDTO)
                .collect(Collectors.toList());
    }

    public User getOneById(Long id) {
        logger.info("Buscando usuário por ID: {}", id);
        Optional<User> optionalUser = this.userRepository.findById(id);
        boolean isPresent = optionalUser.isPresent();
        if (!isPresent) {
            logger.warn("Usuário com ID {} não encontrado.", id);
            return null;
        }
        return optionalUser.get();
    }

    public User create(CreateUserDTO userDto) {
        logger.info("Criando novo usuário.");
        User user = userDto.toUser();
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
        return this.userRepository.save(user);
    }

    public User update(Long id, UpdateUserDTO userDTO) {
        logger.info("Atualizando usuário com ID: {}", id);
        User user = this.getOneById(id);
        if (user == null) {
            logger.warn("Usuário com ID {} não encontrado para atualização.", id);
            return null;
        }
        User userToSave = userDTO.updateUser(user);
        return this.userRepository.save(userToSave);
    }

    public boolean delete(Long id) {
        logger.info("Deletando usuário com ID: {}", id);
        User userToDelete = this.getOneById(id);
        if (userToDelete == null) {
            logger.warn("Usuário com ID {} não encontrado para deleção.", id);
            return false;
        }
        this.userRepository.delete(userToDelete);
        logger.info("Usuário deletado com sucesso. ID: {}", id);
        return true;
    }

    public void updateWallet(User user) {
        logger.info("Atualizando carteira do usuário com ID: {}", user.getId());
        User userToUpdate = getOneById(user.getId());
        userToUpdate.setWallet(user.getWallet());
        userRepository.save(userToUpdate);
        logger.info("Carteira do usuário atualizada com sucesso. ID: {}", user.getId());
    }

    public User getOneByDocument(String document) {
        logger.info("Buscando usuário por documento: {}", document);
        Optional<User> optionalUser = this.userRepository.findByDocument(document);
        boolean isPresent = optionalUser.isPresent();
        if (!isPresent) {
            logger.warn("Usuário com documento {} não encontrado.", document);
            return null;
        }
        return optionalUser.get();
    }

    public UserDetails getOneByEmail(String document) {
        logger.info("Buscando usuário por email: {}", document);
        return this.userRepository.findByEmail(document);
    }
}